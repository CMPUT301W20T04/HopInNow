package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.User;

/**
 * Author: Shway Wang
 * Listener interface listener on rider profile related database activities.
 */
public interface RiderProfileStatusListener {
    /**
     * Called when profile retrieve successfully:
     * @param rider
     *      receives a user object containing all info about the current user.
     */
    void onRiderProfileRetrieveSuccess(Rider rider);

    /**
     * Called when profile retrieve failed:
     */
    void onRiderProfileRetrieveFailure();
    /**
     * Called when profile update successfully:
     */
    void onRiderProfileUpdateSuccess(Rider rider);

    /**
     * Called when profile update failed:
     */
    void onRiderProfileUpdateFailure();
}
