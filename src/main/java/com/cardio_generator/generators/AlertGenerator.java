package com.cardio_generator.generators;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The {@code AlertGenerator} class is responsible for generating alerts, using patient database,
 * using {@code generate}
 */
public class AlertGenerator implements PatientDataGenerator {
    // Used upper snake case, because it is constant
    public static final Random RANDOM_GENERATOR = new Random();
    // Used camel case due to the Java variable naming conventions
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * Function initializes alert states array.
     * @param patientCount corresponds number of observed patients
     */
    public AlertGenerator(int patientCount) {
        //Adopted changed name
        alertStates = new boolean[patientCount + 1];
    }
    /**
     * Function outputs possible alerts due to its probability
     * @param patientId corresponds to the patientID
     * @param outputStrategy corresponds to used output strategy
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            //Adopted changed name
            if (alertStates[patientId]) {
                //Adopted changed name
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    //Adopted changed name
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //Used camel case due to the Java variable naming conventions
                double lamda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                //Adopted changed name
                double p = -Math.expm1(-lamda); // Probability of at least one alert in the period
                //Adopted changed name
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    //Adopted changed name
                    alertStates[patientId] = true;
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
