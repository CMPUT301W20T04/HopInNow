package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Rider;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Required by the database accessors to call when an event happen
 */
public interface RiderObjectRetrieveListener {
    /**
     * When the rider object is retrieved, call this user defined function
     * @param rider
     *      the rider retrieved from the database
     */
    void onRiderObjRetrieveSuccess(Rider rider);

    /**
     * When the rider object is not retrieved, call this user defined function
     */
    void onRiderObjRetrieveFailure();
}
