package com.example.hopinnow.database;

import android.util.Log;

import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.DriverRequestListener;
import com.google.firebase.auth.FirebaseAuth;

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
                        assert request1 != null;
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
     * Driver can delete a request after it is completed
     * @param request
     *      teh request completed, ready to delete
     * @param listener
     *      if the request is deleted successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void driverCompleteRequest(Request request, final DriverRequestListener listener) {
        request.setDriverEmail(null);
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
}
