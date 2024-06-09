package com.alerts;

public class BloodPressureAlert extends Alert {


    public BloodPressureAlert(int patientId, long timestamp) {
        
        super(patientId, "Blood Pressure Alert", timestamp);
    }
}