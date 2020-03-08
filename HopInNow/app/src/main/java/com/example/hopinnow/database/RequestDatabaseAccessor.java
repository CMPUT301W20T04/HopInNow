package com.example.hopinnow.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hopinnow.entities.Request;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class RequestDatabaseAccessor extends DatabaseAccessor {
    public static final String TAG = "RequestDatabaseAccessor";
    private final String referenceName = "availableRequests";

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
                                requests.add((Request)document.getData());
                            }
                            listener.onGetRequiredRequestsSuccess(requests);
                        } else {
                            listener.onGetRequiredRequestsFailure();
                        }
                    }
                });
    }
}
