package com.alerts.factories;

import com.alerts.Alert;
import com.alerts.BloodPressureAlert;
import com.alerts.ECGAlert;

public class ECGAlertFactory extends AlertFactory{

    
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new ECGAlert(patientId, timestamp);
    }

}
