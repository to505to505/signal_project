package com.alerts.decorators;

import com.alerts.Alert;

/**
 * Abstract decorator class for Alerts.
 *
 * This class is designed to extend the functionality of the {@link Alert} class
 * by allowing additional behaviors to be added to existing alerts.
 *
 * It uses the Decorator design pattern to wrap an existing {@link Alert} object
 * and delegates calls to the wrapped object while providing additional
 * functionalities.
 */
public abstract class AlertDecorator extends Alert {
    protected Alert decoratedAlert; // The wrapped Alert object
    private int priority; // Priority of the alert

    /**
     * Constructs an AlertDecorator with the specified Alert to be decorated.
     *
     * @param decoratedAlert the Alert object to be decorated
     */
    public AlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert.getPatientId(), decoratedAlert.getCondition(), decoratedAlert.getTimestamp());
        this.decoratedAlert = decoratedAlert;
    }

    /**
     * Gets the priority of the alert.
     *
     * @return the priority of the alert
     */
    @Override
    public int getPriority() {
        return decoratedAlert.getPriority();
    }

    /**
     * Sets the priority of the alert.
     *
     * @param priority the priority to be set
     */
    @Override
    public void setPriority(int priority) {
        decoratedAlert.setPriority(priority);
    }
}
