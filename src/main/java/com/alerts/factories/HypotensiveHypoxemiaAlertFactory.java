package com.alerts.factories;


import com.alerts.Alert;
import com.alerts.HypotensiveHypoxemiaAlert;

public class HypotensiveHypoxemiaAlertFactory extends AlertFactory {

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new HypotensiveHypoxemiaAlert(patientId, condition, timestamp);
    }
}