package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Request;

import java.util.ArrayList;

/**
 * Author: Shway Wang
 * Listener interface listener on request related database activities.
 */
public interface AvailRequestListListener {

    /**
     * Called when a new request added:
     */
    void onRequestAddedSuccess();

    /**
     * when a new request fails to add:
     */
    void onRequestAddedFailure();

    /**
     * Called when a request deleted successfully:
     */
    void onRequestDeleteSuccess();

    /**
     * Called when a request deleted failed:
     */
    void onRequestDeleteFailure();

    /**
     * Called when get all request success:
     * @param requests
     *      receives an array list of all available request
     */
    void onGetRequiredRequestsSuccess(ArrayList<Request> requests);

    /**
     * Called when get all request fails:
     */
    void onGetRequiredRequestsFailure();
}
