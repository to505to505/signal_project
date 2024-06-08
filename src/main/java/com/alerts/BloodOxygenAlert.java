package com.alerts;

public class BloodOxygenAlert extends Alert {

    public BloodOxygenAlert(String patientId, long timestamp) {

        super(patientId, "Blood Oxygen alert", timestamp);
    }
}
