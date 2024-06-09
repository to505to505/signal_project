package com.alerts;

/**
 * Represents an alert specific to hypotensive hypoxemia conditions.
 *
 * This class extends the {@link Alert} class to provide functionality specific
 * to hypotensive hypoxemia alerts.
 */
public class HypotensiveHypoxemiaAlert extends Alert {

    /**
     * Constructs a HypotensiveHypoxemiaAlert with the specified patient ID and timestamp.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the time at which the alert is created, in milliseconds since the Unix epoch
     */
    public HypotensiveHypoxemiaAlert(int patientId, long timestamp) {
        super(patientId, "HypotensiveHypoxemia Alert", timestamp);
    }
}
