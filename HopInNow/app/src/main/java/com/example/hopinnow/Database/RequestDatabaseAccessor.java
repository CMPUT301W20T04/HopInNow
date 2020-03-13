package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.DriverRequestAcceptListener;
import com.example.hopinnow.statuslisteners.RiderRequestAcceptedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Author: Shway Wang.
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
     * @param request
     *      information of the current request.
     * @param listener
     *      if the request is added successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void addRequest(Request request, final AvailRequestListListener listener) {
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
     * @param listener
     *      if the request is deleted successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void deleteRequest(final AvailRequestListListener listener) {
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
                                    Objects.requireNonNull(task.getResult())) {
                                requests.add(document.toObject(Request.class));
                            }
                            listener.onGetRequiredRequestsSuccess(requests);
                        } else {
                            listener.onGetRequiredRequestsFailure();
                        }
                    }
                });
    }

    public void driverAcceptRequest(Request request, final DriverRequestAcceptListener listener) {
        this.firestore
                .collection(referenceName)
                .document(this.currentUser.getUid())
                .set(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.v(TAG, "Request added!");
                        listener.onDriverRequestAccept();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(TAG, "Request did not save successfully!");
                        listener.onDriverRequestTimeoutOrFail();
                    }
                });
    }

    public void riderWaitForRequestAcceptance(final RiderRequestAcceptedListener listener) {
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
                            listener.onRiderRequestAccept();
                        } else {
                            Log.v(TAG, "Current data: null");
                            listener.onRiderRequestTimeoutOrFail();
                        }
                    }
                });
    }
}
