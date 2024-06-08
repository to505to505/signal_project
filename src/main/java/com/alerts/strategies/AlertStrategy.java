package com.alerts.strategies;

import com.alerts.Alert;
import com.data_management.Patient;

public interface AlertStrategy {
    public Alert checkAlert(Patient patient);
}
