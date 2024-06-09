package com.cardio_generator.outputs;

import javax.xml.crypto.Data;

import com.data_management.DataStorage;

public class ConsoleOutputStrategy implements OutputStrategy {
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        System.out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        DataStorage dataStorage = DataStorage.getInstance();
        dataStorage.addPatientData(patientId, patientId, data, timestamp);

    }
}
