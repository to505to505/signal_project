package com.data_management;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DataAccess {
    private ArrayList<PatientRecord> dataOutput;
    private String path;

    public DataAccess(String path){
        this.path = path;
        dataOutput = new ArrayList<>();
    }

    public ArrayList<PatientRecord> getData() throws IOException {
        FileDataListener stream = new FileDataListener(path);
        dataOutput = dataConverter(stream.getResult());
        return dataOutput;
    }

    private ArrayList<PatientRecord> dataConverter(HashMap<String, Object> input){
        ArrayList<PatientRecord> output = new ArrayList<>();
        ArrayList<LinkedHashMap<String, Object>> patientsData = (ArrayList<LinkedHashMap<String, Object>>) input.get("patients");

        for (LinkedHashMap<String, Object> entry : patientsData) {
            try {
                int patientId = (Integer) entry.get("patientId");
                HashMap<String, Double> metrics = (HashMap<String, Double>) entry.get("metrics");
                long timestamp = ((Integer) entry.get("time stamp")).longValue();

                for (String metricType : metrics.keySet()) {
                    double measurementValue = metrics.get(metricType);
                    PatientRecord instance = new PatientRecord(patientId, measurementValue, metricType, timestamp);
                    output.add(instance);
                }
            } catch (ClassCastException e) {
                System.out.println("Incorrect data format");
                continue;
            }
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        DataAccess dataAccess = new DataAccess("C:\\Users\\mrxst\\Downloads\\ecgdata.json");
        ArrayList<PatientRecord> records = dataAccess.getData();
        // Print out the records for testing purposes
        for (PatientRecord record : records) {
            System.out.println("Patient ID: " + record.getPatientId() +
                    ", Type: " + record.getRecordType() +
                    ", Value: " + record.getMeasurementValue() +
                    ", Timestamp: " + record.getTimestamp());
        }
    }
}

class FileDataListener {
    private HashMap<String, Object> result;

    public HashMap<String, Object> getResult() {
        return result;
    }

    public FileDataListener(String path) throws IOException {
        listenDataAndCloseStream(path);
    }

    public boolean listenDataAndCloseStream(String path) {
        try {
            File fileObj = new File(path);
            result = new ObjectMapper().readValue(fileObj, HashMap.class);
            return true;
        } catch (IOException e) {
            System.out.println("No such file");
            return false;
        }
    }
}
