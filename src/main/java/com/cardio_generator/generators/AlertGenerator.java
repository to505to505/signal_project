package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {
    public static final Random RANDOM_GENERATOR = new Random();
    private boolean[] alertStates;

    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            long timestamp = System.currentTimeMillis();
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    outputStrategy.output(patientId, timestamp, "Alert Resolved", "Alert resolved for patient " + patientId);
                }
            } else {
                int[] systolicReadings = getSystolicReadings(patientId);
                int[] diastolicReadings = getDiastolicReadings(patientId);

                if (isTrendAlert(systolicReadings) || isTrendAlert(diastolicReadings)) {
                    triggerAlert(patientId, "Trend Alert", outputStrategy, timestamp);
                }

                if (isCriticalThresholdAlert(systolicReadings, diastolicReadings)) {
                    triggerAlert(patientId, "Critical Threshold Alert", outputStrategy, timestamp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void triggerAlert(int patientId, String alertType, OutputStrategy outputStrategy, long timestamp) {
        alertStates[patientId] = true;
        outputStrategy.output(patientId, timestamp, alertType, "Patient: " + patientId + " has " + alertType + ", need immediate help");
    }

    private boolean isTrendAlert(int[] readings) {
        if (readings.length < 3) return false;
        for (int i = 2; i < readings.length; i++) {
            if (Math.abs(readings[i] - readings[i - 1]) > 10 && Math.abs(readings[i - 1] - readings[i - 2]) > 10) {
                if ((readings[i] > readings[i - 1] && readings[i - 1] > readings[i - 2]) ||
                        (readings[i] < readings[i - 1] && readings[i - 1] < readings[i - 2])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCriticalThresholdAlert(int[] systolicReadings, int[] diastolicReadings) {
        for (int systolic : systolicReadings) {
            if (systolic > 180 || systolic < 90) return true;
        }
        for (int diastolic : diastolicReadings) {
            if (diastolic > 120 || diastolic < 60) return true;
        }
        return false;
    }

    // Dummy methods to simulate retrieval of readings, replace with actual data access methods
    private int[] getSystolicReadings(int patientId) {
        return new int[]{120, 130, 140}; // Example data
    }

    private int[] getDiastolicReadings(int patientId) {
        return new int[]{80, 85, 90}; // Example data
    }
}
