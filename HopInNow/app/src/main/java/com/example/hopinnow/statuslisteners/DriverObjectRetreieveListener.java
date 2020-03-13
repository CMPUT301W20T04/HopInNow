package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Driver;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 */
public interface DriverObjectRetreieveListener {
    /**
     * Called when a driver object is retrieved successfully
     * @param driver
     *      the driver object retrieved from the database
     */
    void onDriverObjRetrieveSuccess(Driver driver);

    /**
     * Called when a driver object retrieve fails
     */
    void onDriverObjRetrieveFailure();
}
