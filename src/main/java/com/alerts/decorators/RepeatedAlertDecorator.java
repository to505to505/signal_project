package com.alerts.decorators;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;

/**
 * Decorator class that adds repeated alert functionality to an Alert.
 *
 * This class extends the functionality of the {@link AlertDecorator} class by checking
 * for repeated alerts at specified intervals. It uses the Decorator design pattern
 * to wrap an existing {@link AlertDecorator} object and provides additional repeated alert
 * functionality.
 */
public class RepeatedAlertDecorator extends AlertDecorator {
    private Alert alert; // The wrapped Alert object

    /**
     * Constructs a RepeatedAlertDecorator with the specified Alert to be decorated.
     *
     * @param alert the Alert object to be decorated
     * @param interval the interval to check for repeated alerts (currently unused)
     */
    public RepeatedAlertDecorator(Alert alert, long interval) {
        super(alert);
        this.alert = alert;
    }

    /**
     * Checks for repeated alerts.
     *
     * This method simulates checking for repeated alerts by sleeping for a short duration
     * and then invoking the alert evaluation logic from {@link AlertGenerator}.
     */
    public void checkRepeat() {
        System.out.println("Checking for repeated alerts");
        try {
            Thread.sleep(500);
        } catch (InterruptedException exc) {
            Thread.currentThread().interrupt();
        }
        AlertGenerator generator = new AlertGenerator(DataStorage.getInstance());
        try {
            generator.evaluateData(DataStorage.getInstance().getPatient(alert.getPatientId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
