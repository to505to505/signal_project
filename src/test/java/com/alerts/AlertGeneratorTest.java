package com.alerts;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.alerts.strategies.AlertStrategy;
import com.alerts.strategies.BloodPressureStrategy;
import com.alerts.strategies.BloodSaturationStrategy;
import com.alerts.strategies.ECGStrategy;
import com.alerts.strategies.HypotensiveHypoxemiaStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.alerts.Alert;
import com.alerts.AlertGenerator;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlertGeneratorTest {

    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;
    private List<Alert> triggeredAlerts;

    @Before
    public void setUp() {
        dataStorage = new DataStorage();
        triggeredAlerts = new ArrayList<>();
        alertGenerator = new AlertGenerator(dataStorage);
    }

    @Test
    public void testEvaluateDataPressureTriggersAlert() throws IOException {
        // Mocking patient data
        Patient patient = new Patient(1);
        patient.addRecord(180.0, "SystolicPressure", System.currentTimeMillis());

        // Evaluate data
        alertGenerator.evaluateData(patient);

        // Verify that an alert was triggered
        assertFalse(alertGenerator.triggeredAlerts.isEmpty());
    }

    @Test
    public void testEvaluateDataSaturationTriggersAlert() throws IOException {
        // Mocking patient data
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);
        Patient patient = new Patient(1);
        
        patient.addRecord(70, "Saturation", System.currentTimeMillis());
        
        // Evaluate data
        alertGenerator.evaluateData(patient);

        // Verify that an alert was triggered
        assertTrue(alertGenerator.triggeredAlerts.get(0).getCondition().equals("Low Saturation Alert"));
    }

    @Test
    public void testEvaluateDataSaturationNoAlert() throws IOException {
        // Mocking patient data
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);
        Patient patient = new Patient(1);
        
        patient.addRecord(97, "Saturation", System.currentTimeMillis());
        
        // Evaluate data
        alertGenerator.evaluateData(patient);

        // Verify that an alert was triggered
        assertTrue(alertGenerator.triggeredAlerts.isEmpty());
    }
    @Test
    public void testEvaluateDataEcgAlert() throws IOException {
        // Mocking patient data
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);
        Patient patient = new Patient(1);
        long time = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            patient.addRecord(1, "HeartRate",time + i * 3000);
        }
        
        
        // Evaluate data
        alertGenerator.evaluateData(patient);

        // Verify that an alert was triggered
        assertTrue(alertGenerator.triggeredAlerts.get(0).getCondition().equals("Heart rate too slow, ECG alert"));
    }

    @Test
    public void testEvaluateDataEcgNoAlert() throws IOException {
        // Mocking patient data
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);
        Patient patient = new Patient(1);
        long time = System.currentTimeMillis();
        for (int i = 0; i < 60; i++) {
            patient.addRecord(1, "HeartRate",time + i * 1000);
        }
        
        // Evaluate data
        alertGenerator.evaluateData(patient);

        // Verify that an alert was triggered
        assertTrue(alertGenerator.triggeredAlerts.isEmpty());
    }

    @Test
    public void testEvaluateHypotensiveAlert() throws IOException {
        // Mocking patient data
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);
        Patient patient = new Patient(1);
        long time = System.currentTimeMillis();
        
        patient.addRecord(70, "SystolicPressure", time);
        patient.addRecord(70, "Saturation", time);

        
        
        // Evaluate data
        alertGenerator.evaluateData(patient);

        // Verify that an alert was triggered
        assertTrue(alertGenerator.triggeredAlerts.get(0).getCondition().equals("HypotensiveHypoxemia Alert"));
    }









    @Test
    public void testEvaluateDataPressureNoAlert() throws IOException {
        // Mocking patient data
        Patient patient = new Patient(1);
        // patient.addRecord(120.0, "BloodPressure", System.currentTimeMillis());
        patient.addRecord(100.0, "SystolicPressure", System.currentTimeMillis());
        patient.addRecord(100.0, "DiastolicPressure", System.currentTimeMillis());
        // Evaluate data
        alertGenerator.evaluateData(patient);

        // Verify that no alert was triggered
        System.out.println(triggeredAlerts);
        assertTrue(triggeredAlerts.isEmpty());
    }

    // Custom subclass of AlertGenerator to override the triggerAlert method for testing
    private static class TestAlertGenerator extends AlertGenerator {
        private List<Alert> triggeredAlerts;

        public TestAlertGenerator(DataStorage dataStorage, List<Alert> triggeredAlerts) {
            super(dataStorage);
            this.triggeredAlerts = triggeredAlerts;
        }

        
        @Override
        public void triggerAlert(Alert alert) {
            
            triggeredAlerts.add(alert);
        }
    }
}
