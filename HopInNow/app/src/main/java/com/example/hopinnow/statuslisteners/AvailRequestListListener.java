package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Request;

import java.util.ArrayList;

public interface AvailRequestListListener {
    // when a new request added:
    void onRequestAddedSuccess();
    // when a new request fails to add:
    void onRequestAddedFailure();
    // when a request deleted duccessfully:
    void onRequestDeleteSuccess();
    // when a request deleted failed：
    void onRequestDeleteFailure();
    // when get all request success:
    void onGetRequiredRequestsSuccess(ArrayList<Request> requests);
    // when get all request fails:
    void onGetRequiredRequestsFailure();
}
