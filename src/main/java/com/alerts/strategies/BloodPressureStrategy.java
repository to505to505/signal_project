package com.alerts.strategies;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;


public class BloodPressureStrategy implements AlertStrategy {
    private static final BloodPressureAlertFactory alertFactory = new BloodPressureAlertFactory();

    @Override
    public Alert checkAlert(Patient patient) {
      // check trend
      List<PatientRecord> lastThreeRecords = patient.getLastRecords(3, "DiastolicPressure");
      if (lastThreeRecords.size() == 3) {
        if (lastThreeRecords.get(2).getMeasurementValue() - lastThreeRecords.get(1).getMeasurementValue() >= CHANGE_THRESHOLD
            && lastThreeRecords.get(1).getMeasurementValue() - lastThreeRecords.get(0).getMeasurementValue() >= CHANGE_THRESHOLD) {
          return alertFactory.createAlert(patient.getPatientId(), "BloodPressureIncreasingTrendAlert", lastThreeRecords.get(0).getTimestamp());
        }
        if (lastThreeRecords.get(2).getMeasurementValue() - lastThreeRecords.get(1).getMeasurementValue() <= -CHANGE_THRESHOLD
            && lastThreeRecords.get(1).getMeasurementValue() - lastThreeRecords.get(0).getMeasurementValue() <= -CHANGE_THRESHOLD) {
          return alertFactory.createAlert(patient.getPatientId(), "BloodPressureDecreasingTrendAlert", lastThreeRecords.get(0).getTimestamp());
        }
      }

      lastThreeRecords = patient.getLastRecords(3, "SystolicPressure");
      if (lastThreeRecords.size() == 3) {
        if (lastThreeRecords.get(2).getMeasurementValue() - lastThreeRecords.get(1).getMeasurementValue() >= CHANGE_THRESHOLD
            && lastThreeRecords.get(1).getMeasurementValue() - lastThreeRecords.get(0).getMeasurementValue() >= CHANGE_THRESHOLD) {
          return alertFactory.createAlert(patient.getPatientId(), "BloodPressureIncreasingTrendAlert", lastThreeRecords.get(0).getTimestamp());
        }
        if (lastThreeRecords.get(2).getMeasurementValue() - lastThreeRecords.get(1).getMeasurementValue() <= -CHANGE_THRESHOLD
            && lastThreeRecords.get(1).getMeasurementValue() - lastThreeRecords.get(0).getMeasurementValue() <= -CHANGE_THRESHOLD) {
          return alertFactory.createAlert(patient.getPatientId(), "BloodPressureDecreasingTrendAlert", lastThreeRecords.get(0).getTimestamp());
        }
      }

      // check threshold
      PatientRecord lastRecord = patient.getLastRecord("SystolicPressure");
      if (lastRecord != null) {
        if (lastRecord.getMeasurementValue() > HIGH_SYSTOLIC_THRESHOLD) {
          return alertFactory.createAlert(patient.getPatientId(), "BloodPressureOverSystolicThresholdAlert",
              lastRecord.getTimestamp());
        }
        if (lastRecord.getMeasurementValue() < LOW_SYSTOLIC_THRESHOLD) {
          return alertFactory.createAlert(patient.getPatientId(),
              "BloodPressureUnderSystolicThresholdAlert", lastRecord.getTimestamp());
        }
      }


      lastRecord = patient.getLastRecord("DiastolicPressure");
      if (lastRecord != null) {
        if (lastRecord.getMeasurementValue() > HIGH_DIASTOLIC_THRESHOLD) {
          return alertFactory.createAlert(patient.getPatientId(), "BloodPressureOverDiastolicThresholdAlert",
              lastRecord.getTimestamp());
        }
        if (lastRecord.getMeasurementValue() < LOW_DIASTOLIC_THRESHOLD) {
          return alertFactory.createAlert(patient.getPatientId(),
              "BloodPressureUnderDiastolicThresholdAlert", lastRecord.getTimestamp());
        }
      }

      return null;
    }
}