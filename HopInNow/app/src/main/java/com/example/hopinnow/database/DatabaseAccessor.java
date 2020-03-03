package com.example.hopinnow.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseAccessor {
    public static final String TAG = "DatabaseAccessor";
    // Access a Cloud Firestore instance from your Activity
    protected FirebaseFirestore firestore;
    protected FirebaseAuth firebaseAuth;
    protected FirebaseUser currentUser;

    public DatabaseAccessor() {
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }
}
