package com.example.hopinnow.entities;

/**
 * emergency call class which stores the emergency call number
 * Author: Shuwei Wang
 * Version: 1.0.0
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
     */
    public EmergencyCall(String dialogNumber) {
        try{
            this.dialogNumber = dialogNumber;
        }
        catch(Exception e){
            throw e;
        }
    }

    /**
     * get dialog number
     * @return
     */
    public String getDialogNumber() {
        return dialogNumber;
    }

    /**
     * set dialog number
     * @param dialogNumber
     */
    public void setDialogNumber(String dialogNumber) {
        try{
            this.dialogNumber = dialogNumber;
        }
        catch (Exception e){
            throw e;
        }
    }
}

