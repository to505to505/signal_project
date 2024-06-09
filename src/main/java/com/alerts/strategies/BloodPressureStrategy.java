package com.alerts.strategies;

import com.alerts.Alert;
import com.alerts.factories.BloodPressureAlertFactory;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;

import org.testng.annotations.Factory;




public class BloodPressureStrategy implements AlertStrategy {
    public static final int TREND_TRESHOLD = 10;


    public Alert checkTrend( String type, Patient patient, BloodPressureAlertFactory factory) {
      ArrayList<PatientRecord> lastRecords = patient.getRecordsLast(3, type);
      boolean isIncreasing = true;
      boolean isDecreasing = true;
      if (lastRecords.size() == 3) {
        PatientRecord lastRecord = lastRecords.get(0);
        for(int i=1; i<3; i++){
          if(lastRecords.get(i).getMeasurementValue() - lastRecord.getMeasurementValue() <= TREND_TRESHOLD){
            isIncreasing = false;
          }
          if(lastRecords.get(i).getMeasurementValue() - lastRecord.getMeasurementValue() >= -TREND_TRESHOLD){
            isDecreasing = false;
          }
        }
        if(isIncreasing)
            return factory.createAlert(patient.getPatientId(), "Blood Pressure Increasing Alert", lastRecord.getTimestamp());
        if(isDecreasing)
            return factory.createAlert(patient.getPatientId(), "Blood Pressure Decreasing Alert", lastRecord.getTimestamp());
        return null;
      }
      return null;


    }
    @Override
    public Alert checkAlert(Patient patient) {
      BloodPressureAlertFactory factory = new BloodPressureAlertFactory();
      Alert alert = checkTrend("SystolicPressure", patient, factory);
      Alert alert2 = checkTrend("DiastolicPressure", patient, factory);
      if(alert == null) {
        return alert2;
      } else if(alert2 != null) {
        String message1 = alert.getCondition();
        String message2 = alert2.getCondition();

        return factory.createAlert(patient.getPatientId(), "Systolic: " + message1 + " Diastolic: " + message2, TREND_TRESHOLD)
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