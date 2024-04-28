package com.cardio_generator.generators;

import java.util.HashMap;

public class PatientData {
    private int patientID;
    private HashMap<String, Double> metrics;
    private int timestamp;
    public PatientData(int patientID, HashMap<String, Double> metrics, int timestamp) {
        this.patientID = patientID;
        this.metrics = metrics;
        this.timestamp = timestamp;
    }
    public PatientData() {
        metrics = new HashMap<String, Double>();
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public HashMap getMetrics() {
        return metrics;
    }

    public void setMetrics(HashMap metrics) {
        this.metrics = metrics;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
    public void addMetric(String featureName, Double metricVal){
        metrics.put(featureName, metricVal);
    }

}