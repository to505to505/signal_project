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
    ArrayList<PatientRecord> lastRecord = patient.getRecordsLastMinute( "HeartRate");
    ArrayList<Long> beatTimestamps = new ArrayList<>();
    int j =0;
    for (PatientRecord record : lastRecord) {
        if (isBeat(record)) {
            
            beatTimestamps.add(record.getTimestamp());
            if(j>0 && beatTimestamps.get(j-1) - beatTimestamps.get(j-1) < 10)
            j++;
        }
    }


    double totalInterval = 0;
    for (int i = 1; i < beatTimestamps.size(); i++) {
        totalInterval += (beatTimestamps.get(i) - beatTimestamps.get(i - 1));
    }

    double averageInterval = totalInterval / (beatTimestamps.size() - 1);
    double bpm = 60000.0 / averageInterval; 

    
        if (bpm > HEARTRATE_TOP) {
            return new Alert(patient.getPatientId(), "Heart rate too fast, ECG alert", lastRecord.getLast().getTimestamp());
        } else if (bpm < HEARTRATE_BOTTOM) {
            return new Alert(patient.getPatientId(), "Heart rate too slow, ECG alert", lastRecord.getLast().getTimestamp());
        } 

        ArrayList<Long> beatIntervals = new ArrayList<>();
        for (int i = 1; i < beatTimestamps.size(); i++) {
            long interval = beatTimestamps.get(i) - beatTimestamps.get(i - 1);
            beatIntervals.add(interval);
        }
    
        
        double averageIntervalPattern = beatIntervals.stream().mapToLong(Long::longValue).average().orElse(0);
    
        /// 20 percent variation
        double lowerThreshold = averageIntervalPattern * 0.8;
        double upperThreshold = averageIntervalPattern * 1.2;
    
   
        for (long interval : beatIntervals) {
            if (interval < lowerThreshold || interval > upperThreshold) {
                return new Alert(patient.getPatientId(), "ECG strange pattern Alert", lastRecord.getLast().getTimestamp());
                
            }
        }
        return null;
    






}

private boolean isBeat(PatientRecord record) {
   
    return (record.getMeasurementValue()) > 0.3;
}
}
