package com.alerts.factories;

import com.alerts.Alert;
import com.alerts.BloodPressureAlert;

public class BloodPressureAlertFactory extends AlertFactory {

    
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodPressureAlert(patientId, condition, timestamp);
    }
}