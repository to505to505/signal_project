package com.alerts;

public class ECGAlert extends Alert {

    public ECGAlert(String patientId, long timestamp) {
        
        super(patientId, "ECG Alert", timestamp);
    }
}