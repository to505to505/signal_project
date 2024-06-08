package com.alerts.strategies;

import com.alerts.Alert;
import com.alerts.factories.HypotensiveHypoxemiaAlertFactory;
import com.data_management.Patient;
import com.data_management.PatientRecord;


public class HypotensiveHypoxemiaStrategy implements AlertStrategy {

    public static final int SYSTOLICT_TRESHOLD = 90;
    public static final int SATURATION_THRESHOLD = 90;

    private static final HypotensiveHypoxemiaAlertFactory alertFactory = new HypotensiveHypoxemiaAlertFactory();

    @Override
    public Alert checkAlert(Patient patient) {
      boolean lowSystolic = false;
      boolean lowSaturation = false;

      PatientRecord lastRecord = patient.getRecordsLast(1, "SystolicPressure").get(0);
      if (lastRecord != null) {
        if ( lastRecord.getMeasurementValue() <  SYSTOLICT_TRESHOLD) {
        lowSystolic = true;
        }
      }

      PatientRecord lastSaturationRecord = patient.getRecordsLast(1, "Saturation").get(0);
      if (lastSaturationRecord != null) {
        if( lastSaturationRecord.getMeasurementValue() < SATURATION_THRESHOLD)  
                lowSaturation = true;
            

      if (lowSystolic && lowSaturation) {
        return HypotensiveHypoxemiaAlertFactory.createAlert(patient.getPatientId(), "HypotensiveHypoxemia", System.currentTimeMillis());
      }
      return null;
    }
}