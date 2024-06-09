package com.alerts;


public class HypotensiveHypoxemiaAlert extends Alert {
    
    public HypotensiveHypoxemiaAlert(int patientId, long timestamp) {

        super(patientId, "HypotensiveHypoxemia Alert", timestamp);
    }
}