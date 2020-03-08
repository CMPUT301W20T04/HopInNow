package com.example.hopinnow.statuslisteners;
/**
 * Author: Shway Wang
 * Listener interface listener on user registration related database activities.
 */
public interface RegisterStatusListener {

    /**
     * Called when a new user is created, the function spec:
     */
    void onRegisterSuccess();

    /**
     * Called when the signup fails:
     */
    void onRegisterFailure();
}
