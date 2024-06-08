package com.alerts.factories;



import com.alerts.Alert;

public class AlertFactory {

    public Alert createAlert(String patientId, String condition, long timestamp) {
        
        return new Alert(patientId, condition, timestamp);
    }
}
