package com.alerts.strategies;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.ArrayList;

public class ECGStrategy implements AlertStrategy{
    public static final int HEARTRATE_TOP = 100;
    public static final int HEARTRATE_BOTTOM = 50;


@Override
public Alert checkAlert(Patient patient) {
    ArrayList<PatientRecord> lastRecord = patient.getRecordsLastMinute(1, "HeartRate");
    for (PatientRecord record : lastRecord){
        if (lastRecord.getMeasurementValue() > HEARTRATE_TOP) {
            return new Alert(patient.getPatientId(), "Heart rate too fast, ECG alert", lastRecord.getTimestamp());
        } else if (lastRecord.getMeasurementValue() < HEARTRATE_BOTTOM) {
            return new Alert(patient.getPatientId(), "Heart rate too slow, ECG alert", lastRecord.getTimestamp());
        } 


}



return null;
}
}