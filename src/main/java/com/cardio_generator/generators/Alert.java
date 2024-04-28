package com.cardio_generator.generators;

public class Alert {
    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    private int patientID;

    public Alert(int patientID, String condition, int timestamp) {
        this.patientID = patientID;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    private String condition;
    private int timestamp;



}
