package com.example.hopinnow.databasestatuslisteners;

import com.example.hopinnow.entities.User;

public interface UserProfileStatusListener {
    // When profile data stored successfully:
    void onProfileStoreSuccess();
    // When profile data stored fails:
    void onProfileStoreFailure();
    // When profile retreive successfullly:
    void onProfileRetrieveSuccess(User user);
    // When profile retreive failed:
    void onProfileRetrieveFailure();
    // When profile updated successfully:
    void onProfileUpdateSuccess(User user);
    // When profile update fails:
    void onProfileUpdateFailure();
}
