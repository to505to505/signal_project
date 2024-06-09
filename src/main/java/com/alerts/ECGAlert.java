package com.alerts;

public class ECGAlert extends Alert {

    public ECGAlert(int patientId, long timestamp) {
        
        super(patientId, "ECG Alert", timestamp);
    }
}