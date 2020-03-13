package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Request;

public interface RiderRequestAcceptedListener {
    void onRiderRequestAccept(Request request);
    void onRiderRequestTimeoutOrFail();
}
