package com.example.hopinnow.statuslisteners;

/**
 * Author: Shway Wang
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
