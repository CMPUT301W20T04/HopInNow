package com.example.hopinnow.statuslisteners;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Handles the event when Driver accepts a request
 */
public interface DriverRequestListener {
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

    /**
     * Called when the request is cancel by the rider before the driver arrives.
     */
    void onRequestCanceledByRider();

    /**
     * Called when the driver picks up the rider
     */
    void onDriverPickupSuccess();

    /**
     * Called when the pickup fails for some reason
     */
    void onDriverPickupFail();

    /**
     * Called when the driver completes the request
     */
    void onDriverRequestCompleteSuccess();

    /**
     * Called when the driver fails to complete the request
     */
    void onDriverRequestCompleteFailure();

    /**
     * Called when the request is successfully rated by the rider:
     */
    void onWaitOnRatingSuccess();

    /**
     * Called when the request receives an error while waiting for the rating:
     */
    void onWaitOnRatingError();
}
