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
      BloodOxygenAlertFactory factory = new BloodOxygenAlertFactory();
      PatientRecord lastSaturationRecord = patient.getRecordsLast(1, "Saturation").get(0);
      if (lastSaturationRecord != null) 
        if(lastSaturationRecord.getMeasurementValue() < SATURATION_THRESHOLD) {
        return factory.createAlert(patient.getPatientId(), "Low Saturation Alert", System.currentTimeMillis());
      }

      
      ArrayList<PatientRecord> lastTen = patient.getRecordsLastTenMinue( "Saturation");
    
      if (Math.abs(lastTen.getFirst().getMeasurementValue() - lastTen.getLast().getMeasurementValue()) >lastTen.getFirst().getMeasurementValue()*0.05 ) {
            return factory.createAlert(patient.getPatientId(), "Saturation Drop", System.currentTimeMillis());
          }
      

      return null;
    }
}