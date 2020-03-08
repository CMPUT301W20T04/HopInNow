package com.example.hopinnow.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseAccessor {
    public static final String TAG = "DatabaseAccessor";
    // Access a Cloud Firestore instance from your Activity
    protected FirebaseFirestore firestore;  // cloud database
    protected FirebaseAuth firebaseAuth;    // register, login
    protected FirebaseUser currentUser; // logged in current user

    public DatabaseAccessor() {
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }
}
