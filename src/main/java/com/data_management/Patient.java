package com.data_management;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a patient and manages their medical records.
 * This class stores patient-specific data, allowing for the addition and retrieval
 * of medical records based on specified criteria.
 */
public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;

    /**
     * Constructs a new Patient with a specified ID.
     * Initializes an empty list of patient records.
     *
     * @param patientId the unique identifier for the patient
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    /**
     * Adds a new record to this patient's list of medical records.
     * The record is created with the specified measurement value, record type, and
     * timestamp.
     *
     * @param measurementValue the measurement value to store in the record
     * @param recordType       the type of record, e.g., "HeartRate",
     *                         "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in
     *                         milliseconds since UNIX epoch
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }

    /**
     * Retrieves a list of PatientRecord objects for this patient that fall within a
     * specified time range.
     * The method filters records based on the start and end times provided.
     *
     * @param startTime the start of the time range, in milliseconds since UNIX
     *                  epoch
     * @param endTime   the end of the time range, in milliseconds since UNIX epoch
     * @return a list of PatientRecord objects that fall within the specified time
     *         range
     */
    public ArrayList<PatientRecord> getRecords(long startTime, long endTime) {
        ArrayList<PatientRecord> filteredRecords = new ArrayList<>();
        for (PatientRecord record : patientRecords) {
            if (record.getTimestamp() >= startTime && record.getTimestamp() <= endTime) {
                filteredRecords.add(record);
            }
        }
        return filteredRecords;
    }


    public ArrayList<PatientRecord> getRecordsLastMinute(int NumRecords, String recordType) {
        
        ArrayList<PatientRecord> lastRecords = new ArrayList<>();
        if (patientRecords.size() == 0) {
            return null;
            
        } else {
            long timeNow = patientRecords.getLast().getTimestamp();
            return getRecords(timeNow-10000*60, timeNow);
        }
       
        
        
    }

    public ArrayList<PatientRecord> getRecordsLast(int NumRecords, String recordType) {
        int u = 0;
        ArrayList<PatientRecord> lastRecords = new ArrayList<>();
        if (patientRecords.size() == 0) {
            return null;
            
        }
        for (int i = patientRecords.size() - 1; i >= 0; i--) {

            if(u == NumRecords)
                break;

            PatientRecord record = patientRecords.get(i);
            if (record.getRecordType().equals(recordType)) {
                u++;
                lastRecords.add(record);
            }
            
        }
        return lastRecords;
        
    }


    public int getPatientId() {
        return patientId;
    }

    public List<PatientRecord> getPatientRecords() {
        return patientRecords;
    }
}
