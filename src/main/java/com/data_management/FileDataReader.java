package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader {

    public void readData(String outputDir, DataStorage dataStorage) {
        File directory = new File(outputDir);
        dataStorage = DataStorage.getInstance();
        
        if (!directory.exists() ) {
            throw new IllegalArgumentException("The specified output directory does not exist or is not a directory.");
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    parseFile(file, dataStorage);
                }
            }
        }
    }

    private void parseFile(File file, DataStorage dataStorage) {
        dataStorage = DataStorage.getInstance();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int patientId = Integer.parseInt(parts[0]);
                    long timestamp = Long.parseLong(parts[1]);
                    String recordType = parts[2];
                    double measurementValue = Double.parseDouble(parts[3]);

                    dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
