package com.example.hopinnow.entities;

/**
 * Author: Shuwei Wang
 * Version: 1.0.0
 * emergency call class which stores the emergency call number
 */
public class EmergencyCall {
    private String dialogNumber;


    /**
     * empty constructor
     */
    public EmergencyCall() {
        this.dialogNumber = "911";
    }

    /**
     * emergency call constructor
     * @param dialogNumber
     *      the number to dialog
     */
    public EmergencyCall(String dialogNumber) {
        this.dialogNumber = dialogNumber;
    }

    /**
     * get dialog number
     * @return
     *      number to dialog
     */
    public String getDialogNumber() {
        return dialogNumber;
    }

    /**
     * set dialog number
     * @param dialogNumber
     *      number to dialog
     */
    public void setDialogNumber(String dialogNumber) {
        this.dialogNumber = dialogNumber;
    }
}

