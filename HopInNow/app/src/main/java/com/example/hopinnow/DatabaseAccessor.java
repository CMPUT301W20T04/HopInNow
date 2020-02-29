package com.example.hopinnow;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

class DatabaseAccessor {
    public static final String TAG = "DatabaseAccessor";
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore firestore;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;

    DatabaseAccessor() {
        this.firestore = FirebaseFirestore.getInstance();
        this.database = FirebaseDatabase.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    boolean isLoggedin(Context context) {
        return (firebaseAuth.getCurrentUser() != null);
    }

    void loginRider(Context context, Rider rider) {
        final Context finalContext = context;
        // login the rider:
        this.firebaseAuth.signInWithEmailAndPassword(rider.getEmail(), rider.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(finalContext, "Login successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(finalContext, "Login failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void logoutUser(Context context) {
        this.firebaseAuth.signOut();
    }

    void registerRider(Context context, Rider rider) {
        // get a top-level reference to the collection
        final Context finalContext = context;
        // create a user first
        this.firebaseAuth.createUserWithEmailAndPassword(rider.getEmail(), rider.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(finalContext, "registered successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(finalContext, "register failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.loginRider(finalContext, rider);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            this.database.getReference().child(user.getUid()).setValue(rider);
            Toast.makeText(finalContext, "User info saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(finalContext, "Login failed, check internet and re-login!", Toast.LENGTH_SHORT).show();
        }
    }
}
