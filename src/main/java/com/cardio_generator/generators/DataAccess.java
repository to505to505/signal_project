package com.cardio_generator.generators;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataAccess {
    public ArrayList<PatientData> getDataOutput() {
        return dataOutput;
    }

    private ArrayList<PatientData> dataOutput;

    public void setPath(String path) {
        this.path = path;
    }

    private String path;
    public DataAccess(String path){
        this.path = path;
        dataOutput = new ArrayList<PatientData>();
    }
    public ArrayList<PatientData> getData() throws IOException {
        FileDataListener stream = new FileDataListener(path);
        dataOutput = dataConverter(stream.getResult());
        return dataOutput;
    }
    private ArrayList<PatientData> dataConverter(HashMap<String, Object> input){
        ArrayList<PatientData> output = new ArrayList<PatientData>();
        ArrayList<LinkedHashMap<String, Object>> patientsData = (ArrayList<LinkedHashMap<String, Object>>) input.get("patients");
        for(LinkedHashMap entry: patientsData){
            try {
                PatientData instance = new PatientData((Integer)entry.get("patientId"), ((HashMap<String, Double>)entry.get("metrics")), (Integer)entry.get("time stamp"));
                output.add(instance);
            }
            catch (ClassCastException e){
                System.out.println("Incorrect data format");
                continue;
            }
        }
        return output;
    }
    public static void main(String[] args) throws IOException {
        DataAccess a = new DataAccess("C:\\Users\\mrxst\\Downloads\\ecgdata.json");
        a.getData();
    }
}
class FileDataListener{
    public HashMap<String, Object> getResult() {
        return result;
    }

    private HashMap<String, Object> result;

    public FileDataListener(String path) throws IOException {
        listenDataAndCloseStream(path);
    }
    public boolean listenDataAndCloseStream(String path){
        try {
            File fileObj = new File(path);
            result = new ObjectMapper().readValue(fileObj, HashMap.class);
            return true;
        }
        catch (IOException e){
            System.out.println("No such file");
            return false;
        }
    }
}