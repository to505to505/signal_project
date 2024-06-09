package com.alerts;

/**
 * Represents an alert specific to electrocardiogram (ECG) readings.
 *
 * This class extends the {@link Alert} class to provide functionality specific
 * to ECG alerts.
 */
public class ECGAlert extends Alert {

    /**
     * Constructs an ECGAlert with the specified patient ID and timestamp.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the time at which the alert is created, in milliseconds since the Unix epoch
     */
    public ECGAlert(int patientId, long timestamp) {
        super(patientId, "ECG Alert", timestamp);
    }
}
