package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.DriverObjectRetreieveListener;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

/**
 * Author: Shway Wang.
 * Version: 1.0.2
 * UserDatabaseAccessor class extends all access interfaces. Provides all driver related access
 * methods.
 */
public class DriverDatabaseAccessor extends UserDatabaseAccessor {
    public static final String TAG = "DriverDatabaseAccessor";
    public DriverDatabaseAccessor() {
        super();
    }
    /**
     * Update the driver profile according to the User object past in. The key used is the Uid
     * assigned by firestore automatically
     * @param driver
     *      the driver object containing new information
     * @param listener
     *      if the driver is updated successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void updateDriverProfile(final Driver driver, final DriverProfileStatusListener listener) {
        Log.v(TAG, "Ready to create driver profile.");
        // should not let any one see the password!
        driver.setPassword(null);
        // check if logged in:
        super.currentUser = super.firebaseAuth.getCurrentUser();
        if (this.currentUser != null) {
            // the driver is logged in successfully
            Log.v(TAG, "User is logged in!");
            Log.v(TAG, "Ready to store driver information!");
            this.firestore
                    .collection(referenceName)
                    .document(this.currentUser.getUid())
                    .set(driver)
                    .addOnSuccessListener(aVoid -> {
                        Log.v(TAG, "User info updated!");
                        listener.onDriverProfileUpdateSuccess(driver);
                    })
                    .addOnFailureListener(e -> {
                        Log.v(TAG, "User info did not update successfully!");
                        listener.onDriverProfileUpdateFailure();
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
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // check if logged in:
        if (this.currentUser != null) {
            Objects.requireNonNull(this.firestore
                    .collection(super.referenceName)
                    .document(this.currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Log.v(TAG, "Get User Successfully!");
                        if (documentSnapshot.exists()) {
                            listener.onDriverProfileRetrieveSuccess(documentSnapshot
                                    .toObject(Driver.class));
                        }
                    }).addOnFailureListener(e -> {
                        Log.v(TAG, "Get User Failed!");
                        listener.onDriverProfileRetrieveFailure();
                    }));
        } else {    // the driver is not logged in
            Log.v(TAG, "User is not logged in!");
        }
    }

    /**
     * Get the driver object according to the email
     * @param email
     *      email of the driver
     * @param listener
     *      listener called when success or fail.
     */
    public void getDriverObject(String email, final DriverObjectRetreieveListener listener) {
        this.firestore
                .collection(referenceName)
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document :
                                Objects.requireNonNull(task.getResult())) {
                            Log.d(TAG, "Driver got!");
                            listener.onDriverObjRetrieveSuccess(document.toObject(Driver.class));
                        }
                    } else {
                        Log.d(TAG, "Driver did not get!");
                        listener.onDriverObjRetrieveFailure();
                    }
                });
    }
}
