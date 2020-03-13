package com.example.hopinnow.entities;

public class EmergencyCall {
    private String dialogNumber;


    //Constructor
    public EmergencyCall() {
        this.dialogNumber = "911";
    }

    public EmergencyCall(String dialogNumber) {
        try{
            this.dialogNumber = dialogNumber;
        }
        catch(Exception e){
            throw e;
        }
    }

    //getter
    public String getDialogNumber() {
        return dialogNumber;
    }

    //setter
    public void setDialogNumber(String dialogNumber) {
        try{
            this.dialogNumber = dialogNumber;
        }
        catch (Exception e){
            throw e;
        }
    }
}

