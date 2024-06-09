package com.alerts.factories;

import com.alerts.Alert;  
import com.alerts.BloodOxygenAlert;  

public class BloodOxygenAlertFactory extends AlertFactory {
    
    @Override
    public Alert createAlert(int patientId, String condition, long timestamp) {
        return new BloodOxygenAlert(patientId, timestamp);
    }
}