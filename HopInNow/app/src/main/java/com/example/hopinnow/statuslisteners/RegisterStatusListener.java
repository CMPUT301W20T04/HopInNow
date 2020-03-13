package com.example.hopinnow.statuslisteners;
/**
 * Author: Shway Wang
 * Version: 1.0.0
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

    /**
     * Called when the password set by the user is too weak
     */
    void onWeakPassword();

    /**
     * Called when the email set by the user is invalid
     */
    void onInvalidEmail();

    /**
     * Called when the email already exists in the database
     */
    void onUserAlreadyExist();
}
