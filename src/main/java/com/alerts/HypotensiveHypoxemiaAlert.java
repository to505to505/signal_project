package com.alerts;


public class HypotensiveHypoxemiaAlert extends Alert {
    
    public HypotensiveHypoxemiaAlert(String patientId, long timestamp) {

        super(patientId, "HypotensiveHypoxemia Alert", timestamp);
    }
}