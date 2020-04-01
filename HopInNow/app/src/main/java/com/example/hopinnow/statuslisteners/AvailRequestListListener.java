package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Request;

import java.util.ArrayList;

/**
 * Author: Shway Wang
 * Listener interface listener on request related database activities.
 */
public interface AvailRequestListListener {



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

    /**
     * Called when get all request list update success:
     * @param requests
     *      receives an array list of all available request
     */
    void onAllRequestsUpdateSuccess(ArrayList<Request> requests);

    /**
     * Called when get all request fails:
     */
    void onAllRequestsUpdateError();
}
