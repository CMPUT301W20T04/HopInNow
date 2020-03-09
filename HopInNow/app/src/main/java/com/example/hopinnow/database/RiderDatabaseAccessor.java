package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.RiderProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

public class RiderDatabaseAccessor extends UserDatabaseAccessor {
    /**
     * Update the rider profile according to the User object past in. The key used is the Uid
     * assigned by firestore automatically
     * @param rider
     *      the rider object containing new information
     * @param listener
     *      if the rider is updated successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void updateRiderProfile(final Rider rider, final UserProfileStatusListener listener) {
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
                            listener.onProfileUpdateSuccess(rider);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v(TAG, "User info did not update successfully!");
                            listener.onProfileUpdateFailure();
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
}
