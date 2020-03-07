package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hopinnow.statuslisteners.LoginStatusListener;
import com.example.hopinnow.statuslisteners.RegisterStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.example.hopinnow.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.Objects;

public class UserDatabaseAccessor extends DatabaseAccessor {
    public static final String TAG = "UserDatabaseAccessor";
    public UserDatabaseAccessor() {
        super();
    }

    public boolean isLoggedin() {
        return (firebaseAuth.getCurrentUser() != null);
    }

    public void logoutUser() {
        this.firebaseAuth.signOut();
    }

    public void registerUser(User user, final RegisterStatusListener listener) {
        if (this.isLoggedin()) {    // if the user is logged in, logout first
            this.logoutUser();
        }
        // create a rider first
        this.firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.v(TAG, "registered successfully!");
                            listener.onRegisterSuccess();
                        } else {
                            Log.v(TAG, "register failed!");
                            listener.onRegisterFailure();
                        }
                    }
                });
    }

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
                    .collection("Users")
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
                    .collection("Users")
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
    public void getUserProfile(final UserProfileStatusListener listener) {
        this.currentUser = firebaseAuth.getCurrentUser();
        // check if logged in:
        if (this.currentUser != null) {
            Objects.requireNonNull(this.firestore
                    .collection("Users")
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
        } else {    // the user is not logged in
            Log.v(TAG, "User is not logged in!");
        }
    }
}
