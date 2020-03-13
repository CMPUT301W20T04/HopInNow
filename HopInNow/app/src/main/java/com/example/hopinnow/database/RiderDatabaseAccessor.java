package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.DriverObjectRetreieveListener;
import com.example.hopinnow.statuslisteners.RiderObjectRetrieveListener;
import com.example.hopinnow.statuslisteners.RiderProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

/**
 * Author: Shway Wang.
 * UserDatabaseAccessor class extends all access interfaces. Provides all rider related access
 * methods.
 */
public class RiderDatabaseAccessor extends UserDatabaseAccessor {

    public RiderDatabaseAccessor() {
        super();
    }
    /**
     * Update the rider profile according to the User object past in. The key used is the Uid
     * assigned by firestore automatically
     * @param rider
     *      the rider object containing new information
     * @param listener
     *      if the rider is updated successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void updateRiderProfile(final Rider rider, final RiderProfileStatusListener listener) {
        Log.v(TAG, "Ready to create rider profile.");
        // should not let any one see the password!
        rider.setPassword(null);
        // check if logged in:
        this.currentUser = firebaseAuth.getCurrentUser();
        if (this.currentUser != null) {
            // the rider is logged in successfully
            Log.v(TAG, "User is logged in!");
            Log.v(TAG, "Ready to store rider information!");
            this.firestore
                    .collection(referenceName)
                    .document(this.currentUser.getUid())
                    .set(rider)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.v(TAG, "User info updated!");
                            listener.onRiderProfileUpdateSuccess(rider);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v(TAG, "User info did not update successfully!");
                            listener.onRiderProfileUpdateFailure();
                        }
                    });
        } else {    // the rider is not logged in
            Log.v(TAG, "User is not logged in!");
        }
    }
    /**
     * Get the whole set of rider information from the collection "Users". Password is null due to
     * security measures.
     * @param listener
     *      if the rider info is retrieved successfully, call the onSuccess method,
     *      otherwise, onFailure.
     */
    public void getRiderProfile(final RiderProfileStatusListener listener) {
        this.currentUser = firebaseAuth.getCurrentUser();
        // check if logged in:
        if (this.currentUser != null) {
            Objects.requireNonNull(this.firestore
                    .collection(super.referenceName)
                    .document(this.currentUser.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.v(TAG, "Get User Successfully!");
                            if (documentSnapshot.exists()) {
                                listener.onRiderProfileRetrieveSuccess(documentSnapshot
                                        .toObject(Rider.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v(TAG, "Get User Failed!");
                            listener.onRiderProfileRetrieveFailure();
                        }
                    }));
        } else {    // the rider is not logged in
            Log.v(TAG, "User is not logged in!");
        }
    }
    public void getRiderObject(String email, final RiderObjectRetrieveListener listener) {
        this.firestore
                .collection(referenceName)
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document :
                                    Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, "Rider got!");
                                listener.onRiderObjRetrieveSuccess(document.toObject(Rider.class));
                            }
                        } else {
                            Log.d(TAG, "Rider did not get!");
                            listener.onRiderObjRetrieveFailure();
                        }
                    }
                });
    }
}
