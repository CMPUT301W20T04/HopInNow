package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.User;

/**
 * Author: Shway Wang
 * Listener interface listener on driver profile related database activities.
 */
public interface DriverProfileStatusListener {
    /**
     * Called when profile retrieve successfully:
     * @param driver
     *      receives a user object containing all info about the current user.
     */
    void onDriverProfileRetrieveSuccess(Driver driver);

    /**
     * Called when profile retreive failed:
     */
    void onDriverProfileRetrieveFailure();
}
