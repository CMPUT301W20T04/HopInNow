package com.example.hopinnow.database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class DatabaseAccessor {
    public static final String TAG = "DatabaseAccessor";
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    public DatabaseAccessor() {
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean isLoggedin() {
        return (firebaseAuth.getCurrentUser() != null);
    }

    public void logoutUser() {
        this.firebaseAuth.signOut();
    }

    public void registerUser(String TAGstr, User user) {
        final String tag = TAGstr;
        // create a rider first
        this.firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.v(tag, "registered successfully!");
                } else {
                    Log.v(tag, "register failed!");
                }
            }
        });
    }

    public void loginUser(String TAGstr, User user) {
        final String tag = TAGstr;
        // login the rider:
        this.firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.v(tag, "Login successfully!");
                        } else {
                            Log.v(tag, "Login failed!");
                        }
                    }
                });
    }

    public void createUserProfile(String TAGstr, User user) {
        final String tag = TAGstr;
        this.currentUser = firebaseAuth.getCurrentUser();
        if (this.currentUser != null) {
            // the user is logged in successfully
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
        }
    }

    public User getUserProfile(String TAGstr) {
        final String tag = TAGstr;
        this.currentUser = firebaseAuth.getCurrentUser();
        return Objects.requireNonNull(this.firestore
                .collection("Users")
                .document(this.currentUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.v(tag, "Got user profile successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(tag, "Failed to get user profile!");
                    }
                })
                .getResult())
                .toObject(User.class);
    }


}
