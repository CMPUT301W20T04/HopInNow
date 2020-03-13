package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Request;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Handles the events when request is accepted by the driver, and the rider is notified
 */
public interface RiderRequestListener {
    /**
     * Called when the rider's request is accepted by a driver,
     * the request object is returned
     * @param request
     *      the newly updated request object with the driver's email
     */
    void onRiderRequestAcceptedNotify(Request request);

    /**
     * Called when the request made by the rider is not accepted in time or fails to be accepted
     */
    void onRiderRequestTimeoutOrFail();

    void onRiderPickedupSuccess(Request request);

    void onRiderPickedupTimeoutOrFail();

    void onRiderRequestComplete();

    void onRiderRequestCompletionError();
}
