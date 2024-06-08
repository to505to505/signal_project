package com.alerts;

import com.data_management.DataAccess;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private DataAccess dataAccess;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }
    public AlertGenerator(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) throws IOException {
        ArrayList<PatientRecord> patientRecords = dataAccess.getData();

        for (PatientRecord record : patientRecords) {
            if(record.getPatientId() == patient.getPatientId())
                analyzeRecord(record);
        }
    }
    private void analyzeRecord(PatientRecord record) {
        int patientId = record.getPatientId();
        String recordType = record.getRecordType();
        double measurementValue = record.getMeasurementValue();
        long timestamp = record.getTimestamp();

        switch (recordType) {
            case "Heart Rate":
                if (measurementValue < 60 || measurementValue > 100) {
                    triggerAlert(new Alert(Integer.toString(patientId), "Abnormal heart rate: " + measurementValue, (int) timestamp));
                }
                break;
            case "Systolic Blood Pressure":
                if (measurementValue < 90 || measurementValue > 140) {
                    triggerAlert(new Alert(Integer.toString(patientId), "Critical systolic blood pressure: " + measurementValue, (int) timestamp));
                }
                break;
            case "Diastolic Blood Pressure":
                if (measurementValue < 60 || measurementValue > 90) {
                    triggerAlert(new Alert(Integer.toString(patientId), "Critical diastolic blood pressure: " + measurementValue, (int) timestamp));
                }
                break;
            // Add more cases as needed for other measurement types
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        System.out.println("ALERT for Patient " + alert.getPatientId() + ": " + alert.getCondition() + " at " + alert.getTimestamp());
        // Implementation might involve logging the alert or notifying staff
    }
}
