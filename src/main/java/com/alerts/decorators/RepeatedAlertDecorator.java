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
        try {  Thread.sleep(500); 
        } catch (InterruptedException exc) {
            Thread.currentThread().interrupt();
        }
        AlertGenerator generator = new AlertGenerator(DataStorage.getInstance());
        try {generator.evaluateData(DataStorage.getInstance().getPatient(alert.getPatientId()));} 
        catch (Exception e) {
            e.printStackTrace();
    }

}
}