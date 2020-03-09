package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

/**
 * Author: Shway Wang.
 * UserDatabaseAccessor class extends all access interfaces. Provides all driver related access
 * methods.
 */
public class DriverDatabaseAccessor extends UserDatabaseAccessor {
    /**
     * Update the driver profile according to the User object past in. The key used is the Uid
     * assigned by firestore automatically
     * @param driver
     *      the driver object containing new information
     * @param listener
     *      if the driver is updated successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void updateDriverProfile(final Driver driver, final UserProfileStatusListener listener) {
        Log.v(TAG, "Ready to create driver profile.");
        // should not let any one see the password!
        driver.setPassword(null);
        // check if logged in:
        this.currentUser = firebaseAuth.getCurrentUser();
        if (this.currentUser != null) {
            // the driver is logged in successfully
            Log.v(TAG, "User is logged in!");
            Log.v(TAG, "Ready to store driver information!");
            this.firestore
                    .collection(referenceName)
                    .document(this.currentUser.getUid())
                    .set(driver)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.v(TAG, "User info updated!");
                            listener.onProfileUpdateSuccess(driver);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v(TAG, "User info did not update successfully!");
                            listener.onProfileUpdateFailure();
                        }
                    });
        } else {    // the driver is not logged in
            Log.v(TAG, "User is not logged in!");
        }
    }
    /**
     * Get the whole set of driver information from the collection "Users". Password is null due to
     * security measures.
     * @param listener
     *      if the driver info is retrieved successfully, call the onSuccess method,
     *      otherwise, onFailure.
     */
    public void getDriverProfile(final DriverProfileStatusListener listener) {
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
                                listener.onDriverProfileRetrieveSuccess(documentSnapshot
                                        .toObject(Driver.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v(TAG, "Get User Failed!");
                            listener.onDriverProfileRetrieveFailure();
                        }
                    }));
        } else {    // the driver is not logged in
            Log.v(TAG, "User is not logged in!");
        }
    }
}
