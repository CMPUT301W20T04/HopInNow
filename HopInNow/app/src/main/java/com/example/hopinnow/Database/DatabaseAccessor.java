package com.example.hopinnow.Database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Author: Shway Wang
 * Ancestor DatabaseAccessor class, initialized the access interfaces
 */
public class DatabaseAccessor {
    public static final String TAG = "DatabaseAccessor";
    // Access a Cloud Firestore instance from your Activity
    protected FirebaseFirestore firestore;  // cloud database
    protected FirebaseAuth firebaseAuth;    // register, login
    protected FirebaseUser currentUser; // logged in current user

    /**
     * Constructor method, objectify the declarations
     */
    public DatabaseAccessor() {
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }
}
