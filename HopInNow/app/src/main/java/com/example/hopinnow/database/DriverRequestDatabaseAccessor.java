package com.example.hopinnow.database;

import android.util.Log;

import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.DriverRequestListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Author: Shway Wang
 * Version: 1.0.2
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
                .collection(referenceName)
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
                                .collection(referenceName)
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
     * the listener method invokes if the rider cancels the request before the driver arrives.
     * @param request
     *      the request to listen on
     * @param listener
     *      the listener for the request
     */
    public void driverListenOnRequestBeforeArrive(Request request, final DriverRequestListener listener) {
        String requestID = request.getRequestID();
        this.firestore
                .collection(referenceName)
                .document(requestID)
                .addSnapshotListener((documentSnapshot, e) -> {
                    assert documentSnapshot != null;
                    Request request1 = documentSnapshot.toObject(Request.class);
                    if (request1 == null || request1.getRiderEmail() == null) {
                        Log.v(TAG, "The request is canceled by" +
                                "the rider before the driver arrives");
                        listener.onRequestCanceledByRider();
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
    public void driverRequestPickup(Request request, final DriverRequestListener listener) {
        String requestID = request.getRequestID();
        this.firestore
                .collection(referenceName)
                .document(requestID)
                .set(request)
                .addOnSuccessListener(aVoid -> {
                    Log.v(TAG, "Request added!");
                    listener.onDriverPickupSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.v(TAG, "Request did not save successfully!");
                    listener.onDriverPickupFail();
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
                .collection(referenceName)
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
        DocumentReference dr = this.firestore.collection(this.referenceName)
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
