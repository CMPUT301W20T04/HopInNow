package com.example.hopinnow.statuslisteners;

public interface LoginStatusListener {
    // when the user is logged in, the function to do:
    void onLoginSuccess();
    // when the login fails:
    void onLoginFailure();
}