package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hopinnow.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

public class UserDatabaseAccessor extends DatabaseAccessor {

    public boolean isLoggedin() {
        return (firebaseAuth.getCurrentUser() != null);
    }

    public void logoutUser() {
        this.firebaseAuth.signOut();
    }

    public void registerUser(String TAGstr, User user) {
        final String tag = TAGstr;
        final User finalUser = user;
        if (this.isLoggedin()) {    // if the user is logged in, logout first
            this.logoutUser();
        }
        // create a rider first
        this.firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.v(tag, "registered successfully!");
                            loginUser(tag, finalUser);
                        } else {
                            Log.v(tag, "register failed!");
                        }
                    }
                });
    }

    public void loginUser(String TAGstr, User user) {
        final String tag = TAGstr;
        final User finalUser = user;
        // login the rider:
        if (this.isLoggedin()) {
            Log.v(tag, "Login successfully!");
            createUserProfile(tag, finalUser);
        } else {
            this.firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.v(tag, "Login successfully!");
                                createUserProfile(tag, finalUser);
                            } else {
                                Log.v(tag, "Login failed!");
                            }
                        }
                    });
        }
    }

    public void createUserProfile(String TAGstr, User user) {
        Log.v(TAGstr, "Ready to create user profile.");
        // should not let any one see the password!
        user.setPassword(null);
        final String tag = TAGstr;
        // check if logged in:

        this.currentUser = firebaseAuth.getCurrentUser();
        if (this.currentUser != null) {
            // the user is logged in successfully
            Log.v(tag, "User is logged in!");
            Log.v(tag, "Ready to store user information!");
            this.firestore
                    .collection("Users")
                    .document(this.currentUser.getUid())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.v(tag, "User info saved!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v(tag, "User info did not save successfully!");
                        }
                    });
        } else {    // the user is not logged in
            Log.v(tag, "User is not logged in!");
        }
    }

    public User getUserProfile(String TAGstr) {
        final String tag = TAGstr;
        this.currentUser = firebaseAuth.getCurrentUser();
        // sheck if logged in:
        if (this.currentUser != null) {
            final User user = new User();
            Objects.requireNonNull(this.firestore
                    .collection("Users")
                    .document(this.currentUser.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.v(tag, "Get User Successfully!");
                            if (documentSnapshot.exists()) {
                                User tempUser = documentSnapshot.toObject(User.class);
                                user.setSelf(Objects.requireNonNull(tempUser));

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v(tag, "Get User Failed!");
                        }
                    }));
            return user;
        } else {    // the user is not logged in
            Log.v(tag, "User is not logged in!");
            return null;
        }
    }

}
