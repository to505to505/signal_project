package com.main;

import com.alerts.AlertGenerator;
import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataAccess;
import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.Patient;
//import org.junit.Test;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("DataAccess")) {
            DataAccess.main(new String[]{});
        }
        else if (args.length > 0 && args[0].equals("DataStorage")) {
            try {
            DataStorage.main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            HealthDataSimulator.main(args);
        }
    
    AlertGenerator alertGenerator = new AlertGenerator(DataStorage.getInstance());
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    FileDataReader fileDataReader = new FileDataReader("dataFolder/");
    fileDataReader.readData(DataStorage.getInstance());
    DataStorage dataStorage = DataStorage.getInstance();
    System.out.println(dataStorage.getAllPatients().size());

    
    while (true) {
        for(Patient patient : DataStorage.getInstance().getAllPatients()) {
            
            alertGenerator.evaluateData(patient);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
}