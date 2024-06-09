package com.alerts.factories;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlert;

/**
 * Factory class for creating BloodOxygenAlert instances.
 *
 * This class extends {@link AlertFactory} to provide a method for creating instances of the {@link BloodOxygenAlert} class.
 */
public class BloodOxygenAlertFactory extends AlertFactory {

    /**
     * Creates a BloodOxygenAlert instance with the specified patient ID, condition, and timestamp.
     *
     * @param patientId the unique identifier of the patient
     * @param condition the condition associated with the alert
     * @param timestamp the time at which the alert is created, in milliseconds since the Unix epoch
     * @return a new BloodOxygenAlert instance
     */
    @Override
    public Alert createAlert(int patientId, String condition, long timestamp) {
        return new BloodOxygenAlert(patientId, condition, timestamp);
    }
}
