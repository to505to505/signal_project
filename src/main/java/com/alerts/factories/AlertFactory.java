package com.alerts.factories;

import com.alerts.Alert;

/**
 * Factory class for creating Alert instances.
 *
 * This class provides a method for creating instances of the {@link Alert} class
 * with specified patient ID, condition, and timestamp.
 */
public class AlertFactory {

    /**
     * Creates an Alert instance with the specified patient ID, condition, and timestamp.
     *
     * @param patientId the unique identifier of the patient
     * @param condition the condition associated with the alert
     * @param timestamp the time at which the alert is created, in milliseconds since the Unix epoch
     * @return a new Alert instance
     */
    public Alert createAlert(int patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp);
    }
}
