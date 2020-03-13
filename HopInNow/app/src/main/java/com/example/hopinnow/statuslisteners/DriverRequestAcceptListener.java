package com.example.hopinnow.statuslisteners;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Handles the event when Driver accepts a request
 */
public interface DriverRequestAcceptListener {
    /**
     * Called when the driver successfully accepts a request
     */
    void onDriverRequestAccept();

    /**
     * Called when the driver fails to accept the request
     */
    void onDriverRequestTimeoutOrFail();

    /**
     * Called when the request is already taken by some other drivers
     */
    void onRequestAlreadyTaken();
}
