package com.example.hopinnow;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

class DatabaseAccessor {
    public static final String TAG = "DatabaseAccessor";
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore database;

    DatabaseAccessor() {
        this.database = FirebaseFirestore.getInstance();
    }

    void signupRider(Rider rider) {
        // get a top-level reference to the collection
        this.database
                .collection("Riders")
                .document(rider.getName())
                .set(rider)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // happens when data added successfully
                        Log.d(TAG, "Data addition successful!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // happens when data added failed
                        Log.d(TAG, "Data addition failed" + e.toString());
                    }
                }).isSuccessful();
    }


}
