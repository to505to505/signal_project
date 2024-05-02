package com.cardio_generator.outputs;

/**
 * The {@code OutputStrategy} class is responsible for outputting patient data,
 * using {@code output}
 */
public interface OutputStrategy {
    /**
     * Function outputs data for certain patient
     *
     * @param patientId is the patient ID
     * @param timestamp is the timestamp of the record
     * @param label is the data's label
     * @param data is the patient data
     */
    void output(int patientId, long timestamp, String label, String data);
}
