package com.alerts.decorators;

import com.alerts.Alert;


public class PriorityAlertDecorator extends AlertDecorator {
    private int priority;

    public PriorityAlertDecorator(Alert alert, int priority) {
        super(alert);
        this.priority = priority;
        
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
}