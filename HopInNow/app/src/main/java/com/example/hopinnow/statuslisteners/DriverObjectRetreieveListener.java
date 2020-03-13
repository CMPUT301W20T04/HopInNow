package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Driver;

public interface DriverObjectRetreieveListener {
    void onDriverObjRetrieveSuccess(Driver driver);
    void onDriverObjRetrieveFailure();
}
