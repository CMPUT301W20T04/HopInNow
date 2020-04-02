package com.example.hopinnow.statuslisteners;

public interface RequestAddDeleteListener {
    /**
     * Called when a new request added:
     */
    void onRequestAddedSuccess();

    /**
     * when a new request fails to add:
     */
    void onRequestAddedFailure();

    /**
     * Called when a request deleted successfully:
     */
    void onRequestDeleteSuccess();

    /**
     * Called when a request deleted failed:
     */
    void onRequestDeleteFailure();
}
