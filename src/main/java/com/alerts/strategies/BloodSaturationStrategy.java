package com.alerts.strategies;



import com.alerts.Alert;
import com.alerts.factories.BloodOxygenAlertFactory;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

import java.util.ArrayList;


public class BloodSaturationStrategy implements AlertStrategy {
    public static final int SATURATION_THRESHOLD = 92;

    
    @Override
    public Alert checkAlert(Patient patient) {
      try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
      PatientRecord lastRecord = null;
      BloodOxygenAlertFactory factory = new BloodOxygenAlertFactory();
      ArrayList<PatientRecord> lastSaturationRecordLists = patient.getRecordsLast(1, "Saturation");
      if (lastSaturationRecordLists.size() == 0) {
        return null;
        
      } else {
        lastRecord= lastSaturationRecordLists.get(0);
      }
      if (lastRecord != null) {
        if(lastRecord.getMeasurementValue() < SATURATION_THRESHOLD) {
        return factory.createAlert(patient.getPatientId(), "Low Saturation Alert", System.currentTimeMillis());
      }
    }

      
      ArrayList<PatientRecord> lastTen = patient.getRecordsLastTenMinue( "Saturation");
    
      if (Math.abs(lastTen.get(0).getMeasurementValue() - lastTen.get(lastTen.size()-1).getMeasurementValue()) >lastTen.get(0).getMeasurementValue()*0.05 ) {
            return factory.createAlert(patient.getPatientId(), "Saturation Drop", System.currentTimeMillis());
          }
      

      return null;
    }
}