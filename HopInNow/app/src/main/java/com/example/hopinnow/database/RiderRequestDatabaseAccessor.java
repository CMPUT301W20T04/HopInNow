package com.example.hopinnow.database;

import android.util.Log;

import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.RiderRequestListener;
import com.google.firebase.auth.FirebaseAuth;

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
    public void riderWaitForRequestAcceptance(final RiderRequestListener listener) {
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

    /**
     * invoke the listener when rider is picked up
     * @param listener
     *      listener called when success or fail or timeout
     */
    public void riderWaitForPickup(final RiderRequestListener listener) {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firestore
                .collection(this.referenceName)
                .document(this.currentUser.getUid())
                .addSnapshotListener((snapshot, e) -> {
                    Request request = Objects.requireNonNull(snapshot).toObject(Request.class);
                    if (e != null) {
                        Log.v(TAG, "Listen failed.", e);
                        listener.onRiderPickedupTimeoutOrFail();
                    }
                    if (snapshot.exists()) {
                        if (Objects.requireNonNull(request).isPickedUp()) {
                            Log.v(TAG, "Got data: ");
                            listener.onRiderPickedupSuccess(snapshot.toObject(Request.class));
                        }
                    } else {
                        Log.v(TAG, "Current data: null");
                        listener.onRiderPickedupTimeoutOrFail();
                    }
                });
    }

    /**
     * Rider is pickedup, now to wait for the request to complete
     * @param listener
     *      invoke method when the rider is dropped off
     */
    public void riderWaitForRequestComplete(final RiderRequestListener listener) {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firestore
                .collection(this.referenceName)
                .document(this.currentUser.getUid())
                .addSnapshotListener((snapshot, e) -> {
                    Request request = Objects.requireNonNull(snapshot).toObject(Request.class);
                    if (e != null) {
                        Log.v(TAG, "Listen failed.", e);
                        listener.onRiderRequestCompletionError();
                    }
                    if (!snapshot.exists()) {
                        Log.v(TAG, "Got data: ");
                        listener.onRiderRequestComplete();
                    }
                });
    }
}
