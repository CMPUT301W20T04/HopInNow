package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.RiderRequestListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Author: Shway Wang
 * Version: 1.1.0
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
        DocumentReference ref = this.firestore.collection(this.referenceName)
                .document(this.currentUser.getUid());
        // this function can help remove the snapshot listeners
        super.listenerRegistration = ref.addSnapshotListener((snapshot, e) -> {
            Request request = Objects.requireNonNull(snapshot).toObject(Request.class);
            if (e != null) {
                Log.v(TAG, "riderWaitForRequestAcceptance failed.", e);
                listener.onRiderRequestTimeoutOrFail();
                // if an error happens, stops listening:
                super.listenerRegistration.remove();
            }
            if (snapshot.exists()) {
                if (Objects.requireNonNull(request).getDriverEmail() != null) {
                    Log.v(TAG, "riderWaitForRequestAcceptance: " +
                            "driver accepted request.");
                    listener.onRiderRequestAcceptedNotify(snapshot.toObject(Request.class));
                    // if the driver accepts the rider's request, then stops listening:
                    super.listenerRegistration.remove();
                }
            } else {
                Log.v(TAG, "riderWaitForRequestAcceptance data: null");
                listener.onRiderRequestTimeoutOrFail();
                // if there is an error while listening to the request, then stops listening:
                super.listenerRegistration.remove();
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
     *      RiderRequestListener class object
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
                    } else if (acceptStatus == -1) {
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
        DocumentReference ref = this.firestore.collection(this.referenceName)
                .document(this.currentUser.getUid());
        super.listenerRegistration = ref.addSnapshotListener((snapshot, e) -> {
                    Request request = Objects.requireNonNull(snapshot).toObject(Request.class);
                    if (e != null) {
                        Log.v(TAG, "Listen failed.", e);
                        listener.onRiderPickedupTimeoutOrFail();
                        // if there is an error while driver is trying to pick up the rider, then
                        // stops listening to the snapshot:
                        super.listenerRegistration.remove();
                    }
                    if (snapshot.exists()) {
                        if (Objects.requireNonNull(request).isPickedUp()) {
                            Log.v(TAG, "rider picked up: ");
                            listener.onRiderPickedupSuccess(snapshot.toObject(Request.class));
                            // if the rider is now picked up, then stops listening:
                            super.listenerRegistration.remove();
                        }
                    } else {
                        Log.v(TAG, "Current data: null");
                        listener.onRiderPickedupTimeoutOrFail();
                        // if there is an error while driver is trying to pick up the rider, then
                        // stops listening to the snapshot:
                        super.listenerRegistration.remove();
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
        DocumentReference ref = this.firestore.collection(this.referenceName)
                .document(this.currentUser.getUid());
        super.listenerRegistration = ref.addSnapshotListener((snapshot, e) -> {
                    Request request = Objects.requireNonNull(snapshot).toObject(Request.class);
                    if (e != null) {
                        Log.v(TAG, "Listen failed.", e);
                        listener.onRiderDropoffFail();
                        // if there is an error while driver is dropping off, then stop listening:
                        super.listenerRegistration.remove();
                    }
                    if (snapshot.exists()) {
                        if (Objects.requireNonNull(request).isArrivedAtDest()) {
                            Log.v(TAG, "rider dropped off: ");
                            listener.onRiderDropoffSuccess(snapshot.toObject(Request.class));
                            // if the rider is dropped off successfully, stops listening:
                            super.listenerRegistration.remove();
                        }
                    } else {
                        Log.v(TAG, "Current data: null");
                        listener.onRiderDropoffFail();
                        // if the dropping off fails, stops listening:
                        super.listenerRegistration.remove();
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
        DocumentReference ref = this.firestore.collection(this.referenceName)
                .document(this.currentUser.getUid());
        super.listenerRegistration = ref.addSnapshotListener((snapshot, e) -> {
            Log.v(TAG, "rider complete caught snapshot");
            if (e != null) {
                Log.v(TAG, "Listen failed.", e);
                listener.onRiderRequestCompletionError();
                // if there is an error while the ride is completing, stops listening:
                super.listenerRegistration.remove();
            }
            if (Objects.requireNonNull(snapshot).exists()) {
                Request request = Objects.requireNonNull(snapshot).toObject(Request.class);
                if (Objects.requireNonNull(request).isComplete()) {
                    Log.v(TAG, "ride completed: ");
                    listener.onRiderRequestComplete();
                    // if the request is complete, stops listening:
                    super.listenerRegistration.remove();
                } else {
                    Log.v(TAG, "Listen failed.", e);
                    listener.onRiderRequestCompletionError();
                    // if the request fails to complete, stops listening:
                    super.listenerRegistration.remove();
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
