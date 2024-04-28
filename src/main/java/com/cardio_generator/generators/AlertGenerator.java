package com.cardio_generator.generators;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    private boolean[] AlertStates; // false = resolved, true = pressed

    public AlertGenerator(int patientCount) {
        AlertStates = new boolean[patientCount + 1];
    }

    public void evaluateData(PatientData patient, PatientDataCriteria criteria){
        HashMap<String, Double> metrics = patient.getMetrics();
        for(Map.Entry<String, Double> metric: metrics.entrySet()){
            if(criteria.checkCriteria(metric.getKey(), metric.getValue()) == false) {
                triggerAlert(new Alert(patient.getPatientID(), metric.getKey(), (int)System.currentTimeMillis()));
            }
        }
    }
    public void triggerAlert(Alert alert){
        System.out.println("Patient: " + alert.getPatientID() + " has " + alert.getCondition() + ", need immediate help");
    }
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (AlertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    AlertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    AlertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
class PatientDataCriteria{


    private HashMap<String, CriteriaBounds> criterias;
    public PatientDataCriteria(){
        criterias = new HashMap<String, CriteriaBounds>();
    }
    public PatientDataCriteria(HashMap<String, CriteriaBounds> criterias) {
        this.criterias = criterias;
    }
    public void addCriteria(String featureName, Double leftBound, Double rightBound){
        criterias.put(featureName, new CriteriaBounds(leftBound, rightBound));
    }
    public boolean checkCriteria(String featureName, Double val){
        if(criterias.get(featureName).getLeftBound() > val || criterias.get(featureName).getRightBound() < val)
            return false;
        return true;
    }
}
class CriteriaBounds{
    public Double getLeftBound() {
        return leftBound;
    }
    public Double getRightBound() {
        return rightBound;
    }

    public CriteriaBounds(Double leftBound, Double rightBound) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    Double leftBound;
    Double rightBound;

}
