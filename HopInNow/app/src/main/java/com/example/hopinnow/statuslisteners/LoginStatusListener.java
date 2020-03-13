package com.example.hopinnow.statuslisteners;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Listener interface listener on user login related database activities.
 */
public interface LoginStatusListener {

    /**
     * Called when the user is logged in, the function to do:
     */
    void onLoginSuccess();

    /**
     * Called when the login fails:
     */
    void onLoginFailure();
}
