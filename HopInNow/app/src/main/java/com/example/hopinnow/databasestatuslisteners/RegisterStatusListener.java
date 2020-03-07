package com.example.hopinnow.databasestatuslisteners;

public interface RegisterStatusListener {
    // when a new user is created, the function spec:
    void onRegisterSuccess();
    // when the signup fails:
    void onRegisterFailure();
}
