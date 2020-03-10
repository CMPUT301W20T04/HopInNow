package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.LoginStatusListener;
import com.example.hopinnow.statuslisteners.RegisterStatusListener;
import com.example.hopinnow.statuslisteners.RiderProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.example.hopinnow.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.Objects;

/**
 * Author: Shway Wang.
 * UserDatabaseAccessor class extends all access interfaces. Provides all user related access
 * methods.
 */
public class UserDatabaseAccessor extends DatabaseAccessor {
    public static final String TAG = "UserDatabaseAccessor";
    protected final String referenceName = "Users";

    /**
     * Constructor, default setting, call super().
     */
    public UserDatabaseAccessor() {
        super();
    }

    /**
     * Determine whether the current user is logged in or not
     * @return
     *      returns a boolean, if logged in, true; other wise, false.
     */
    public boolean isLoggedin() {
        return (firebaseAuth.getCurrentUser() != null);
    }

    /**
     * Void method to log out the current logged in user
     */
    public void logoutUser() {
        this.firebaseAuth.signOut();
    }

    /**
     * Create a new user record in the database according to the information in the past in user
     * object.
     * @param user
     *      the user object containing information to create the new record in database
     * @param listener
     *      if the record is added successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void registerUser(User user, final RegisterStatusListener listener) {
        if (this.isLoggedin()) {    // if the user is logged in, logout first
            this.logoutUser();
        }
        // create a user.
        this.firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.v(TAG, "registered successfully!");
                            listener.onRegisterSuccess();
                        } else {
                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                Log.v(TAG, "onComplete: weak_password");
                                listener.onWeakPassword();
                            } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                Log.v(TAG, "onComplete: malformed_email");
                                listener.onInvalidEmail();
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                Log.v(TAG, "onComplete: exist_email");
                                listener.onUserAlreadyExist();
                            } catch (Exception e) {
                                Log.v(TAG, "onComplete: " + e.getMessage());
                                listener.onRegisterFailure();
                            }
                        }
                    }
                });
    }

    /**
     * Login the user according to the email and password contained in the user object past in.
     * @param user
     *      the user object containing the information required to login.
     * @param listener
     *      if the user is logged in successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void loginUser(User user, final LoginStatusListener listener) {
        // login the rider:
        if (this.isLoggedin()) {
            Log.v(TAG, "Login successfully!");
            listener.onLoginSuccess();
        } else {
            this.firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.v(TAG, "Login successfully!");
                                listener.onLoginSuccess();
                            } else {
                                Log.v(TAG, "Login failed!");
                                listener.onLoginFailure();
                            }
                        }
                    });
        }
    }

    /**
     * Add the detailed information of the user to a separate part in the database. password is set
     * to null due to security measures.
     * @param user
     *      the object containing all information of the current user, including email and password.
     * @param listener
     *      if the info is stored successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void createUserProfile(User user, final UserProfileStatusListener listener) {
        Log.v(TAG, "Ready to create user profile.");
        // should not let any one see the password!
        user.setPassword(null);
        // check if logged in:
        this.currentUser = firebaseAuth.getCurrentUser();
        if (this.currentUser != null) {
            // the user is logged in successfully
            Log.v(TAG, "User is logged in!");
            Log.v(TAG, "Ready to store user information!");
            this.firestore
                    .collection(referenceName)
                    .document(this.currentUser.getUid())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.v(TAG, "User info saved!");
                            listener.onProfileStoreSuccess();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v(TAG, "User info did not save successfully!");
                            listener.onProfileStoreFailure();
                        }
                    });
        } else {    // the user is not logged in
            Log.v(TAG, "User is not logged in!");
        }
    }

    /**
     * Update the user profile according to the User object past in. The key used is the Uid
     * assigned by firestore automatically
     * @param user
     *      the user object containing new information
     * @param listener
     *      if the user is updated successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void updateUserProfile(final User user, final UserProfileStatusListener listener) {
        Log.v(TAG, "Ready to create user profile.");
        // should not let any one see the password!
        user.setPassword(null);
        // check if logged in:
        this.currentUser = firebaseAuth.getCurrentUser();
        if (this.currentUser != null) {
            // the user is logged in successfully
            Log.v(TAG, "User is logged in!");
            Log.v(TAG, "Ready to store user information!");
            this.firestore
                    .collection(referenceName)
                    .document(this.currentUser.getUid())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.v(TAG, "User info updated!");
                            listener.onProfileUpdateSuccess(user);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v(TAG, "User info did not update successfully!");
                            listener.onProfileUpdateFailure();
                        }
                    });
        } else {    // the user is not logged in
            Log.v(TAG, "User is not logged in!");
        }
    }
    /**
     * Get the whole set of user information from the collection "Users". Password is null due to
     * security measures.
     * @param listener
     *      if the user info is retrieved successfully, call the onSuccess method,
     *      otherwise, onFailure.
     */
    public void getUserProfile(final UserProfileStatusListener listener) {
        this.currentUser = firebaseAuth.getCurrentUser();
        // check if logged in:
        if (this.currentUser != null) {
            Objects.requireNonNull(this.firestore
                    .collection(this.referenceName)
                    .document(this.currentUser.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.v(TAG, "Get User Successfully!");
                            if (documentSnapshot.exists()) {
                                listener.onProfileRetrieveSuccess(documentSnapshot
                                        .toObject(User.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v(TAG, "Get User Failed!");
                            listener.onProfileRetrieveFailure();
                        }
                    }));
        } else {    // the driver is not logged in
            Log.v(TAG, "User is not logged in!");
        }
    }
}
