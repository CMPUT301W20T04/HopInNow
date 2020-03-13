package com.example.hopinnow.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Author: Shway Wang
 * Ancestor DatabaseAccessor class, initialized the access interfaces
 */
public class DatabaseAccessor {
    public static final String TAG = "DatabaseAccessor";
    //private static final DatabaseAccessor instance = new DatabaseAccessor();
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore firestore;  // cloud database
    FirebaseAuth firebaseAuth;    // register, login
    FirebaseUser currentUser; // logged in current user

    public static DatabaseAccessor databaseAccessor = null;

    /**
     * Constructor method, objectify the declarations
     */
    protected DatabaseAccessor() {
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }
    public static DatabaseAccessor getInstance() {
        if (databaseAccessor == null) {
            databaseAccessor = new DatabaseAccessor();
        }
        return databaseAccessor;
    }
}
