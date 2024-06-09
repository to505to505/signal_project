package com.alerts.decorators;

import com.alerts.Alert;

/**
 * Decorator class that adds priority to an Alert.
 *
 * This class extends the functionality of the {@link Alert} class by adding
 * a priority attribute. It uses the Decorator design pattern to wrap an
 * existing {@link Alert} object and provides additional priority functionality.
 */
public class PriorityAlertDecorator extends AlertDecorator {
    private int priority; // Priority of the alert

    /**
     * Constructs a PriorityAlertDecorator with the specified Alert to be decorated and the given priority.
     *
     * @param alert the Alert object to be decorated
     * @param priority the priority to be assigned to the alert
     */
    public PriorityAlertDecorator(Alert alert, int priority) {
        super(alert);
        this.priority = priority;
    }

    /**
     * Gets the priority of the alert.
     *
     * @return the priority of the alert
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the alert.
     *
     * @param priority the priority to be set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
