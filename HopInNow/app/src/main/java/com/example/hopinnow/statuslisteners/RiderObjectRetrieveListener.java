package com.example.hopinnow.statuslisteners;

import com.example.hopinnow.entities.Rider;

public interface RiderObjectRetrieveListener {
    void onRiderObjRetrieveSuccess(Rider rider);
    void onRiderObjRetrieveFailure();
}
