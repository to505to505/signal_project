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
    private TestAlertGenerator alertGenerator;
    private List<Alert> triggeredAlerts;

    @Before
    public void setUp() {
        dataStorage = new DataStorage();
        triggeredAlerts = new ArrayList<>();
        alertGenerator = new TestAlertGenerator(dataStorage, triggeredAlerts);
    }

    @Test
    public void testEvaluateDataTriggersAlert() throws IOException {
        // Mocking patient data
        Patient patient = new Patient(1);
        patient.addRecord(180.0, "BloodPressure", System.currentTimeMillis());

        // Evaluate data
        alertGenerator.evaluateData(patient);

        // Verify that an alert was triggered
        assertFalse(triggeredAlerts.isEmpty());
    }

    @Test
    public void testEvaluateDataNoAlert() throws IOException {
        // Mocking patient data
        Patient patient = new Patient(1);
        patient.addRecord(120.0, "BloodPressure", System.currentTimeMillis());

        // Evaluate data
        alertGenerator.evaluateData(patient);

        // Verify that no alert was triggered
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
