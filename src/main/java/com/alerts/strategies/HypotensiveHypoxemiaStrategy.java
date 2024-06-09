package com.alerts.strategies;

import java.lang.reflect.Array;

import com.alerts.Alert;
import com.alerts.factories.HypotensiveHypoxemiaAlertFactory;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;


public class HypotensiveHypoxemiaStrategy implements AlertStrategy {

    public static final int SYSTOLICT_TRESHOLD = 90;
    public static final int SATURATION_THRESHOLD = 92;


    @Override
    public Alert checkAlert(Patient patient) {
      boolean lowSystolic = false;
      boolean lowSaturation = false;

      PatientRecord lastRecord = null;

      ArrayList<PatientRecord> lastSystolicRecords = patient.getRecordsLast(1, "SystolicPressure");
        if(lastSystolicRecords.size() == 0) {
            return null;
        }
        else {
            lastRecord = lastSystolicRecords.get(0);
        }

        
      if (lastRecord != null) {
        if ( lastRecord.getMeasurementValue() <  SYSTOLICT_TRESHOLD) {
        lowSystolic = true;
        }
      }

      ArrayList<PatientRecord>  lastSaturationRecords = patient.getRecordsLast(1, "Saturation");
      if(lastSaturationRecords.size() == 0) {
        return null;
      } else {
        lastRecord = lastSaturationRecords.get(0);
      }
      if (lastRecord != null) {
        if( lastRecord.getMeasurementValue() < SATURATION_THRESHOLD)  
                lowSaturation = true;
      }
            

      if (lowSystolic && lowSaturation) {
        HypotensiveHypoxemiaAlertFactory factory = new HypotensiveHypoxemiaAlertFactory();
        return factory.createAlert(patient.getPatientId(),"HypotensiveHypoxemia Alert", System.currentTimeMillis());
      }
        
    
    return null;
}
}