package com.alerts.factories;

import com.alerts.Alert;
import com.alerts.BloodPressureAlert;

/**
 * Factory class for creating BloodPressureAlert instances.
 *
 * This class extends {@link AlertFactory} to provide a method for creating instances of the {@link BloodPressureAlert} class.
 */
public class BloodPressureAlertFactory extends AlertFactory {

    /**
     * Creates a BloodPressureAlert instance with the specified patient ID and timestamp.
     *
     * @param patientId the unique identifier of the patient
     * @param condition the condition associated with the alert
     * @param timestamp the time at which the alert is created, in milliseconds since the Unix epoch
     * @return a new BloodPressureAlert instance
     */
    @Override
    public Alert createAlert(int patientId, String condition, long timestamp) {
        return new BloodPressureAlert(patientId, timestamp);
    }
}
