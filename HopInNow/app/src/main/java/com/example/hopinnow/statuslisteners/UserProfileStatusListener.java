package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.User;

/**
 * Author: Shway Wang
 * Listener interface listener on user profile related database activities.
 */
public interface UserProfileStatusListener {
    /**
     * Called when profile data stored successfully:
     */
    void onProfileStoreSuccess();

    /**
     * Called when profile data stored fails:
     */
    void onProfileStoreFailure();

    /**
     * Called when profile retrieve successfully:
     * @param user
     *      receives a user object containing all info about the current user.
     */
    void onProfileRetrieveSuccess(User user);

    /**
     * Called when profile retreive failed:
     */
    void onProfileRetrieveFailure();

    /**
     * Called when profile updated successfully:
     * @param user
     *      receives the updated user object.
     */
    void onProfileUpdateSuccess(User user);

    /**
     * Called when profile update fails:
     */
    void onProfileUpdateFailure();
}
