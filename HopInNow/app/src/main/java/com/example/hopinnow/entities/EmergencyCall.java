package com.example.hopinnow.entities;

public class EmergencyCall {
    private String dialogNumber;


    //Constructor
    public EmergencyCall() {
        this.dialogNumber = "911";
    }

    public EmergencyCall(String dialogNumber) {
        this.dialogNumber = dialogNumber;
    }

    //getter
    public String getDialogNumber() {
        return dialogNumber;
    }

    //setter
    public void setDialogNumber(String dialogNumber) {
        this.dialogNumber = dialogNumber;
    }
}

