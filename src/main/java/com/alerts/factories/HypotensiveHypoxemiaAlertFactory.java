package com.alerts.factories;

import com.alerts.Alert;
import com.alerts.HypotensiveHypoxemiaAlert;

/**
 * Factory class for creating HypotensiveHypoxemiaAlert instances.
 *
 * This class extends {@link AlertFactory} to provide a method for creating instances of the {@link HypotensiveHypoxemiaAlert} class.
 */
public class HypotensiveHypoxemiaAlertFactory extends AlertFactory {

    /**
     * Creates a HypotensiveHypoxemiaAlert instance with the specified patient ID and timestamp.
     *
     * @param patientId the unique identifier of the patient
     * @param condition the condition associated with the alert (not used for HypotensiveHypoxemiaAlert)
     * @param timestamp the time at which the alert is created, in milliseconds since the Unix epoch
     * @return a new HypotensiveHypoxemiaAlert instance
     */
    @Override
    public Alert createAlert(int patientId, String condition, long timestamp) {
        return new HypotensiveHypoxemiaAlert(patientId, timestamp);
    }
}
