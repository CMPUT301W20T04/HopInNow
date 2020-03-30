package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Request;

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
     * Called when the request info is changed by rider before driver arrives
     */
    void onRequestInfoChange(Request request);

    /**
     * Called when the request is accepted by the rider
     */
    void onRequestAcceptedByRider(Request request);

    /**
     * Called when the request is declined by the rider before the driver arrives.
     */
    void onRequestDeclinedByRider();

    /**
     * Called when the driver picks up the rider
     */
    void onDriverPickupSuccess();

    /**
     * Called when the pickup fails for some reason
     */
    void onDriverPickupFail();

    /**
     * Called when the driver drop off the rider
     */
    void onDriverDropoffSuccess(Request request);

    /**
     * Called when the drop off fails for some reason
     */
    void onDriverDropoffFail();

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
