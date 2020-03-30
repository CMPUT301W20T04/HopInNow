package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.RiderRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;
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
     * invoke the listener when request is accepted or declined by the rider.
     * @param acceptStatus
     *      1: request accepted by rider
     *      0: request neither accepted nor declined by rider
     *      -1: request declined by rider
     * @param listener
     *      listener called when success or fail
     */
    public void riderAcceptOrDeclineRequest(int acceptStatus, final RiderRequestListener listener) {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Map<String, Object> map = new HashMap<>();
        map.put("acceptStatus", acceptStatus);
        if (acceptStatus == -1) {
            map.put("car", null);
            map.put("driverEmail", null);
        }
        this.firestore
                .collection(this.referenceName)
                .document(this.currentUser.getUid())
                .update(map)
                .addOnSuccessListener(aVoid -> {
                    if (acceptStatus == 1) {
                        Log.v(TAG, "the request is accepted by the rider.");
                        listener.onRiderAcceptDriverRequest();
                    } else {
                        Log.v(TAG, "the request is declined by the rider.");
                        listener.onRiderDeclineDriverRequest();
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
                            Log.v(TAG, "rider picked up: ");
                            listener.onRiderPickedupSuccess(snapshot.toObject(Request.class));
                        }
                    } else {
                        Log.v(TAG, "Current data: null");
                        listener.onRiderPickedupTimeoutOrFail();
                    }
                });
    }

    /**
     * invoke the listener when rider is dropped off
     * @param listener
     *      listener called when success or fail or timeout
     */
    public void riderWaitForDropoff(final RiderRequestListener listener) {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firestore
                .collection(this.referenceName)
                .document(this.currentUser.getUid())
                .addSnapshotListener((snapshot, e) -> {
                    Request request = Objects.requireNonNull(snapshot).toObject(Request.class);
                    if (e != null) {
                        Log.v(TAG, "Listen failed.", e);
                        listener.onRiderDropoffFail();
                    }
                    if (snapshot.exists()) {
                        if (Objects.requireNonNull(request).isAD()) {
                            Log.v(TAG, "rider picked up: ");
                            listener.onRiderDropoffSuccess(snapshot.toObject(Request.class));
                        }
                    } else {
                        Log.v(TAG, "Current data: null");
                        listener.onRiderDropoffFail();
                    }
                });
    }

    /**
     * Rider is dropped off, now to wait for the request to complete
     * @param listener
     *      invoke method when the rider is dropped off
     */
    public void riderWaitForRequestComplete(final RiderRequestListener listener) {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference dr = this.firestore.collection(this.referenceName)
                .document(this.currentUser.getUid());
        dr.addSnapshotListener((snapshot, e) -> {
            Log.v(TAG, "rider complete caught snapshot");
            if (e == null) {
                Log.v(TAG, "Listen failed.", e);
                listener.onRiderRequestCompletionError();
            }
            if (Objects.requireNonNull(snapshot).exists()) {
                Request request = Objects.requireNonNull(snapshot).toObject(Request.class);
                if (Objects.requireNonNull(request).isComplete()) {
                    Log.v(TAG, "ride completed: ");
                    listener.onRiderRequestComplete();
                } else {
                    Log.v(TAG, "Listen failed.", e);
                    listener.onRiderRequestCompletionError();
                }
            }
        });
    }

    /**
     * The current request is finished, rider now can rate the request
     * @param request
     *      the rating for the current request is set in this request
     * @param listener
     *      invoke method when the rider finishes rating the request
     */
    public void riderRateRequest(Request request, final RiderRequestListener listener) {
        this.firestore
                .collection(referenceName)
                .document(request.getRequestID())
                .set(request)
                .addOnSuccessListener(aVoid -> {
                    Log.v(TAG, "Request completed!");
                    listener.onRequestRatedSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.v(TAG, "Request did not complete successfully!");
                    listener.onRequestRatedError();
                });
    }
}
