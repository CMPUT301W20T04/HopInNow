package com.example.hopinnow.statuslisteners;

public interface AvailRequestListListener {
    // when a new request added:
    void onRequestAddedSuccess();
    // when a new request fails to add:
    void onRequestAddedFailure();
    // when a request deleted duccessfully:
    void onRequestDeleteSuccess();
    // when a request deleted failed：
    void onRequestDeleteFailure();
}
