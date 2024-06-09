package com.alerts;

import com.alerts.strategies.BloodPressureStrategy;
import com.alerts.strategies.BloodSaturationStrategy;
import com.alerts.strategies.ECGStrategy;
import com.alerts.strategies.HypotensiveHypoxemiaStrategy;
import com.beust.ah.A;
import com.data_management.DataStorage;
import com.data_management.Patient;



import java.io.IOException;
import java.util.ArrayList;

import com.alerts.strategies.AlertStrategy;
/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    public ArrayList<Alert> triggeredAlerts;
    
    private final ArrayList<AlertStrategy> alertStrategies;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        
        this.triggeredAlerts = new ArrayList<>();
        this.dataStorage = dataStorage;
        this.alertStrategies = new ArrayList<>();
        initializeAlertStrategies();
    }
   

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) throws IOException {
        
        for(AlertStrategy strategy : alertStrategies){
            Alert alert = strategy.checkAlert(patient);
            if(alert != null){
                triggerAlert(alert);
            }

        }
       
    
    }
        

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    public void triggerAlert(Alert alert) {
        System.out.println("Patient  " + alert.getPatientId() +" has the following alrt:  " +alert.getCondition() + ". Time:  " + alert.getTimestamp());
        triggeredAlerts.add(alert);
        
        
        // Implementation might involve logging the alert or notifying staff
    }

    private void initializeAlertStrategies() {
        this.alertStrategies.add(new HypotensiveHypoxemiaStrategy());
        this.alertStrategies.add(new BloodPressureStrategy());
        this.alertStrategies.add(new BloodSaturationStrategy());
        this.alertStrategies.add(new ECGStrategy());
    }
    public static void main(String[] args) {
        Patient patient = new Patient(1);
        patient.addRecord(70, "Saturation", System.currentTimeMillis());
        AlertGenerator alertGenerator = new AlertGenerator(new DataStorage());

        try {
            alertGenerator.evaluateData(patient);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(alertGenerator.triggeredAlerts.get(0).getCondition());
        System.out.println(patient.getRecordsLast(1, "Saturation"));
    }
}
