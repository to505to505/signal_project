package com.alerts.decorators;

import com.alerts.Alert;

public abstract class AlertDecorator extends Alert {
    protected Alert decoratedAlert;
    private int priority;


    public AlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert.getPatientId(), decoratedAlert.getCondition(), decoratedAlert.getTimestamp());
        this.decoratedAlert = decoratedAlert;
    }

    
    

    @Override
    public int getPriority() {
        return decoratedAlert.getPriority();
    }

    @Override
    public void setPriority(int priority) {
        decoratedAlert.setPriority(priority);
    }
}
