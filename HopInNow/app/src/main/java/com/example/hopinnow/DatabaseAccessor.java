package com.example.hopinnow;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseAccessor {
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db;

    public DatabaseAccessor () {
        this.db = FirebaseFirestore.getInstance();
    }

    public void signupRider (Rider rider) {
        // get a top-level reference to the collection
        final CollectionReference collectionReference = this.db.collection("Users");
        collectionReference
                .document(rider.getName())
                .set(rider)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // happens when data added successfully
                        //Log.d(TAG, "Data addition successful!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // happens when data added failed
                        //Log.d(TAG, "Data addition failed" + e.toString());
                    }
                });
    }
}
