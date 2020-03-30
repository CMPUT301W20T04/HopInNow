package com.example.hopinnow.database;

import android.util.Log;

import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.DriverRequestListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Author: Shway Wang
 * Version: 1.0.4
 */
public class DriverRequestDatabaseAccessor extends RequestDatabaseAccessor {
    public static final String TAG = "DriverRequestDA";
    public DriverRequestDatabaseAccessor() {
        super();
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    /**
     * Set the driver attribute of the request to the current driver user.
     * @param request
     *      the request the driver wants to accept
     * @param listener
     *      called when success or fail.
     */
    public void driverAcceptRequest(Request request, final DriverRequestListener listener) {
        String requestID = request.getRequestID();
        // get the request object to inspect
        this.firestore
                .collection(super.referenceName)
                .document(requestID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Request request1 =
                                requireNonNull(task.getResult()).toObject(Request.class);
                        if (request1 == null) {
                            Log.v(TAG, "Request did not accept successfully!");
                            listener.onDriverRequestTimeoutOrFail();
                            return;
                        }
                        // check the driverEmail of the request see if it already exists:
                        if (request1.getDriverEmail() != null) {
                            Log.v(TAG, "Request is already taken!");
                            // if it is, invoke the appropriate listener and return:
                            listener.onRequestAlreadyTaken();
                            return;
                        }
                        // if driverEmail does not exist, put in the current driver email:
                        firestore
                                .collection(super.referenceName)
                                .document(requestID)
                                .set(request)
                                .addOnSuccessListener(aVoid -> {
                                    Log.v(TAG, "Request added!");
                                    listener.onDriverRequestAccept();
                                })
                                .addOnFailureListener(e -> {
                                    Log.v(TAG, "Request did not save successfully!");
                                    listener.onDriverRequestTimeoutOrFail();
                                });
                    } else {
                        Log.v(TAG, "Request did not save successfully!");
                        listener.onDriverRequestTimeoutOrFail();
                    }
                });
    }
    /**
     * Called for the driver to listen on the request he or she just accepted,
     * the listener method invokes if the rider accepts or declines the request before the driver
     * arrives.
     * @param request
     *      the request to listen on
     * @param listener
     *      the listener for the request
     */
    public void driverListenOnRequestBeforeArrive(Request request, final DriverRequestListener listener) {
        String requestID = request.getRequestID();
        this.firestore
                .collection(super.referenceName)
                .document(requestID)
                .addSnapshotListener((documentSnapshot, e) -> {
                    assert documentSnapshot != null;
                    Request req = documentSnapshot.toObject(Request.class);
                    if (requireNonNull(req).getAcceptStatus() == 1) {
                        // acceptStatus is 1 means request is accepted
                        Log.v(TAG, "The request is accepted by the rider.");
                        listener.onRequestAcceptedByRider(req);
                    } else if (req.getAcceptStatus() == -1 || req.getRiderEmail() == null) {
                        // acceptStatus is -1 means request is declined
                        Log.v(TAG, "The request is declined by" +
                                "the rider before the driver arrives");
                        listener.onRequestDeclinedByRider();
                    } else {
                        // acceptStatus is 0 means request is neither accepted nor declined yet
                        Log.v(TAG, "The request info is changed.");
                        listener.onRequestInfoChange(req);
                    }
                });
    }

    /**
     * Should only set the isPickedUp to true in the request list
     * @param request
     *      request object to change
     * @param listener
     *      the listener to invoke the methods
     */
    public void driverPickupRider(Request request, final DriverRequestListener listener) {
        String requestID = request.getRequestID();
        Map<String, Object> map = new HashMap<>();
        map.put("pickedUp", true);
        Log.v(TAG, "ready to put isPickedUp == true into database.");
        this.firestore
                .collection(super.referenceName)
                .document(requestID)
                .update(map)
                .addOnSuccessListener(aVoid -> {
                    Log.v(TAG, "picked up success!");
                    listener.onDriverPickupSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.v(TAG, "picked up fail!");
                    listener.onDriverPickupFail();
                });
    }

    /**
     * Should only set the isAD to true in the request list
     * @param request
     *      request object to change
     * @param listener
     *      the listener to invoke the methods
     */
    public void driverDropoffRider(Request request, final DriverRequestListener listener) {
        String requestID = request.getRequestID();
        Map<String, Object> map = new HashMap<>();
        map.put("arrivedAtDest", true);
        this.firestore
                .collection(super.referenceName)
                .document(requestID)
                .update(map)
                .addOnSuccessListener(aVoid -> {
                    Log.v(TAG, "Drop off success!");
                    listener.onDriverDropoffSuccess(request);
                })
                .addOnFailureListener(e -> {
                    Log.v(TAG, "Drop off fail!");
                    listener.onDriverDropoffFail();
                });
    }

    /**
     * This function sets the isComplete attribute in a request in the database to true.
     * @param request
     *      the request that is completed
     * @param listener
     *      if the isComplete attribute is changed successfully,
     *      call the onSuccess method, otherwise, onFailure.
     */
    public void driverCompleteRequest(Request request, final DriverRequestListener listener) {
        // because the request is now complete:
        request.setComplete(true);
        this.firestore
                .collection(super.referenceName)
                .document(request.getRequestID())
                .set(request)
                .addOnSuccessListener(aVoid -> {
                    Log.v(TAG, "Request completed!");
                    listener.onDriverRequestCompleteSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.v(TAG, "Request did not complete successfully!");
                    listener.onDriverRequestCompleteFailure();
                });
    }

    /**
     * Driver waits on the rider to make the rating on the current request. After it is rated, the
     * caller of this function can specify the actions to take in the onRequestRatedSuccess method
     * or the onRequestRatedError method.
     * @param request
     *      the request with the requestID to listen to
     * @param listener
     *      if the request is rated successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void driverWaitOnRating(Request request, final DriverRequestListener listener) {
        DocumentReference dr = this.firestore.collection(super.referenceName)
                .document(request.getRequestID());
        dr.addSnapshotListener((snapshot, e) -> {
            Request req = Objects.requireNonNull(snapshot).toObject(Request.class);
            Log.v(TAG, "driver wait for rating caught snapshot");
            if (e == null) {
                Log.v(TAG, "Listen failed.", e);
                listener.onWaitOnRatingError();
            }
            if (snapshot.exists()) {
                // see if the rating actually has changed:
                if (Objects.requireNonNull(req).getRating() != -1.0) {
                    Log.v(TAG, "request rated: ");
                    listener.onWaitOnRatingSuccess();
                } else {
                    Log.v(TAG, "request rated failed.", e);
                    listener.onWaitOnRatingError();
                }
            }
        });
    }
}
