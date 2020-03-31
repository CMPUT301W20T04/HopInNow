package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hopinnow.entities.LatLong;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.RequestAddDeleteListener;
import com.example.hopinnow.statuslisteners.RiderRequestListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.util.Objects.requireNonNull;

/**
 * Author: Shway Wang.
 * Version: 1.0.2
 * This class is the database accessor providing all methods relating to ride requests.
 */
public class RequestDatabaseAccessor extends DatabaseAccessor {
    public static final String TAG = "RequestDatabaseAccessor";
    protected final String referenceName = "availableRequests";

    /**
     * Default constructor, calls super();
     */
    public RequestDatabaseAccessor() {
        super();
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * Add or update a (new) request to the availableRequests collection.
     * Note: this method should only be called by the rider,
     * if this method is called by the driver, the action is unspecified.
     * @param request
     *      information of the current request.
     * @param listener
     *      if the request is added successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void addUpdateRequest(Request request, final RequestAddDeleteListener listener) {
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
                .addOnSuccessListener(aVoid -> {
                    Log.v(TAG, "Request added!");
                    listener.onRequestAddedSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.v(TAG, "Request did not save successfully!");
                    listener.onRequestAddedFailure();
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
    public void deleteRequest(final RequestAddDeleteListener listener) {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firestore
                .collection(referenceName)
                .document(this.currentUser.getUid())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.v(TAG, "Request deleted!");
                    listener.onRequestDeleteSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.v(TAG, "Request did not delete successfully!");
                    listener.onRequestDeleteFailure();
                });
    }

    /**
     * Get all available requests as an ArrayList object from collection availableRequests
     * @param latLong
     *      the latitude and longitude of the current user(usually the driver)
     * @param listener
     *      if all requests are retrieved successfully, call the onSuccess method,
     *      otherwise, onFailure.
     */
    public void getAllRequest(LatLong latLong, final AvailRequestListListener listener) {
        this.firestore
                .collection(referenceName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Request> requests = new ArrayList<>();
                        for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
                            Request request = document.toObject(Request.class);
                            if (request.getDriverEmail() == null) {
                                LatLong tempLatLong = request.getPickUpLoc();
                                // calculate the manhattan distance:
                                request.setMdToDriver(Math.abs((latLong.getLat() - tempLatLong.getLat())
                                        + (latLong.getLng() - tempLatLong.getLng())));
                                requests.add(request);
                            }
                        }
                        // sort all requests according to manhattan distance
                        Collections.sort(requests);
                        listener.onGetRequiredRequestsSuccess(requests);
                    } else {
                        listener.onGetRequiredRequestsFailure();
                    }
                });
    }

    /**
     * Listening on all changes about all available requests, return as an ArrayList object from
     * the collection availableRequests
     * @param latLong
     *      the latitude and longitude of the current user
     * @param listener
     *      if all requests are retrieved successfully, call the onSuccess method,
     *      otherwise, onFailure.
     */
    public void listenOnAllRequests(LatLong latLong, final AvailRequestListListener listener) {
        this.firestore
                .collection(referenceName)
                .whereEqualTo("driverEmail", null)
                .addSnapshotListener((value, e) -> {
                    Log.v(TAG, "an event happened!");
                    if (e != null) {
                        Log.v(TAG, "event listening failed.", e);
                        listener.onAllRequestsUpdateError();
                        return;
                    }
                    ArrayList<Request> requests = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : requireNonNull(value)) {
                        if (doc.exists() && doc.get("riderEmail") != null) {
                            Request request = doc.toObject(Request.class);
                            Log.v(TAG, (String) requireNonNull(doc.get("riderEmail")));
                            LatLong tempLatLong = request.getPickUpLoc();
                            // calculate manhattan distance(the distance needs to be absolute):
                            request.setMdToDriver(Math.abs((latLong.getLat() - tempLatLong.getLat())
                                    + (latLong.getLng() - tempLatLong.getLng())));
                            requests.add(request);
                        }
                    }
                    Log.v(TAG, "requests updated!");
                    // sort all requests according to manhattan distance
                    Collections.sort(requests);
                    listener.onAllRequestsUpdateSuccess(requests);
                });

    }
}
