package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.RiderRequestAcceptedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Handles all rider side database accesses
 */
public class RiderRequestDatabaseAccessor extends RequestDatabaseAccessor {
    public static final String TAG = "RiderRequestDA";
    public RiderRequestDatabaseAccessor() {
        super();
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    /**
     * invoke the listener when request is accepted by a driver
     * @param listener
     *      listener called when success or fail or timeout
     */
    public void riderWaitForRequestAcceptance(final RiderRequestAcceptedListener listener) {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firestore
                .collection(this.referenceName)
                .document(this.currentUser.getUid())
                .addSnapshotListener((snapshot, e) -> {
                    Request request = Objects.requireNonNull(snapshot).toObject(Request.class);
                    if (e != null) {
                        Log.v(TAG, "Listen failed.", e);
                        listener.onRiderRequestTimeoutOrFail();
                    }
                    if (snapshot.exists()) {
                        if (Objects.requireNonNull(request).getDriverEmail() != null) {
                            Log.v(TAG, "Got data: ");
                            listener.onRiderRequestAcceptedNotify(snapshot.toObject(Request.class));
                        }
                    } else {
                        Log.v(TAG, "Current data: null");
                        listener.onRiderRequestTimeoutOrFail();
                    }
                });
    }
}
