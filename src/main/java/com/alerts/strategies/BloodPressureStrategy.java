package com.alerts.strategies;

import com.alerts.Alert;
import com.alerts.factories.BloodPressureAlertFactory;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;

import org.testng.annotations.Factory;




public class BloodPressureStrategy implements AlertStrategy {
    public static final int TREND_TRESHOLD = 10;
    public static final int DIASTOLIC_THRESHOLD_TOP = 180;
    public static final int DIASTOLIC_THRESHOLD_BOTTOM = 90;
    public static final int SYSTOLIC_THRESHOLD_TOP = 120;
    public static final int SYSTOLIC_THRESHOLD_BOTTOM = 60;


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
      if(alert == null && alert2 == null) {
        
      } else if(alert == null) {
          return alert2;
      } else if(alert2 == null) {
        return alert;
      }  else {
    
        String message1 = alert.getCondition();
        String message2 = alert2.getCondition();

        return factory.createAlert(patient.getPatientId(), "Systolic: " + message1 + " Diastolic: " + message2, TREND_TRESHOLD);
      } 
      

      
      ArrayList<PatientRecord> lastRecordsSys = patient.getRecordsLast(1, "SystolicPressure");
      PatientRecord lastRecordSys = null;
      PatientRecord lastRecordDia = null;
      if (lastRecordsSys.size() == 0) {
        System.out.println("No records found");
        return null;
        
      } else {
        lastRecordSys = lastRecordsSys.get(0);
      }
      if (lastRecordSys != null) {
        System.out.println("High Systolic Blood Pressure Alert");
        if (lastRecordSys.getMeasurementValue() > SYSTOLIC_THRESHOLD_TOP) {
          System.out.println("High Systolic Blood Pressure Alert");
          return factory.createAlert(patient.getPatientId(), "High Systolic Blood Pressure Alert",
              lastRecordSys.getTimestamp());
        }
        if (lastRecordSys.getMeasurementValue() < SYSTOLIC_THRESHOLD_BOTTOM) {
          return factory.createAlert(patient.getPatientId(), "Low Systolic Blood Pressure Alert",
              lastRecordSys.getTimestamp());
        }
      }
      ArrayList<PatientRecord> lastRecordsDia = patient.getRecordsLast(1, "DiastolicPressure");
      if (lastRecordsDia.size() == 0) {
        return null;
        
      } else {
        lastRecordDia = lastRecordsDia.get(0);
      }
      
      if (lastRecordDia != null) {
        if (lastRecordDia.getMeasurementValue() > DIASTOLIC_THRESHOLD_TOP) {
          return factory.createAlert(patient.getPatientId(), "High Diastolic Blood Pressure Alert",
              lastRecordDia.getTimestamp());
        }
        if (lastRecordDia.getMeasurementValue() < DIASTOLIC_THRESHOLD_BOTTOM) {
          return factory.createAlert(patient.getPatientId(), "Low Diastolic Blood Pressure Alert",
              lastRecordDia.getTimestamp());
        }
      }
      
      return null;
    }
    
}