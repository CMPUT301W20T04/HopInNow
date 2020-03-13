package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.DriverRequestAcceptListener;
import com.example.hopinnow.statuslisteners.RiderRequestAcceptedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Author: Shway Wang.
 * Version: 1.0.1
 * This class is the database accessor providing all methods relating to ride requests.
 */
public class RequestDatabaseAccessor extends DatabaseAccessor {
    public static final String TAG = "RequestDatabaseAccessor";
    private final String referenceName = "availableRequests";

    /**
     * Default constructor, calls super();
     */
    public RequestDatabaseAccessor() {
        super();
    }

    /**
     * Add a new request to the availableRequests collection.
     * Note: this method should only be called by the rider,
     * if this method is called by the driver, the action is unspecified.
     * @param request
     *      information of the current request.
     * @param listener
     *      if the request is added successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void addRequest(Request request, final AvailRequestListListener listener) {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (this.currentUser == null) {
            Log.v(TAG, "user is not logged in!!!");
            return;
        } else {
            Log.v(TAG, "user is logged in!!!");
            Log.v(TAG, requireNonNull(this.currentUser.getEmail()));
        }
        String myUid = this.currentUser.getUid();
        request.setRequestID(myUid);
        this.firestore
                .collection(referenceName)
                .document(this.currentUser.getUid())
                .set(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.v(TAG, "Request added!");
                        listener.onRequestAddedSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(TAG, "Request did not save successfully!");
                        listener.onRequestAddedFailure();
                    }
                });
    }

    /**
     * Delete a request from the availableRequests collection.
     * Note: only the rider can create or delete a request.
     * This method should be called only by the rider, if the driver invoke
     * this method, the action is unspecified.
     * @param listener
     *      if the request is deleted successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void deleteRequest(final AvailRequestListListener listener) {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firestore
                .collection(referenceName)
                .document(this.currentUser.getUid())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.v(TAG, "Request deleted!");
                        listener.onRequestDeleteSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(TAG, "Request did not delete successfully!");
                        listener.onRequestDeleteFailure();
                    }
                });
    }

    /**
     * Get all available requests as an ArrayList object from collection availableRequests
     * @param listener
     *      if all requests are retrieved successfully, call the onSuccess method,
     *      otherwise, onFailure.
     */
    public void getAllRequest(final AvailRequestListListener listener) {
        this.firestore
                .collection(referenceName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Request> requests = new ArrayList<>();
                            for (QueryDocumentSnapshot document :
                                    requireNonNull(task.getResult())) {
                                Request request = document.toObject(Request.class);
                                if (request.getDriverEmail() == null) {
                                    requests.add(request);
                                }
                            }
                            listener.onGetRequiredRequestsSuccess(requests);
                        } else {
                            listener.onGetRequiredRequestsFailure();
                        }
                    }
                });
    }

    /**
     * Set the driver attribute of the request to the current driver user.
     * @param request
     *      the request the driver wants to accept
     * @param listener
     *      called when success or fail.
     */
    public void driverAcceptRequest(Request request, final DriverRequestAcceptListener listener) {
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
     * invoke the listener when request is accepted by a driver
     * @param listener
     *      listener called when success or fail or timeout
     */
    public void riderWaitForRequestAcceptance(final RiderRequestAcceptedListener listener) {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firestore
                .collection(this.referenceName)
                .document(this.currentUser.getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.v(TAG, "Listen failed.", e);
                            listener.onRiderRequestTimeoutOrFail();
                        }
                        if (snapshot != null && snapshot.exists()) {
                            Log.v(TAG, "Got data: ");
                            listener.onRiderRequestAcceptedNotify(snapshot.toObject(Request.class));
                        } else {
                            Log.v(TAG, "Current data: null");
                            listener.onRiderRequestTimeoutOrFail();
                        }
                    }
                });
    }
}
