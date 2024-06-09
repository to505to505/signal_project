package com.alerts.decorators;



import com.alerts.Alert;
import com.alerts.decorators.AlertDecorator;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;

public class RepeatedAlertDecorator extends AlertDecorator {
    private Alert alert;


    public RepeatedAlertDecorator(Alert alert, long interval) {
        super(alert);
        this.alert = alert;
      
    }

    public void checkRepeat() {
        System.out.println("Checking for repeated alerts");
        // AlertGenerator.evaluateData(DataStorage.getPatient(alert.getPatientId()));
    }

}