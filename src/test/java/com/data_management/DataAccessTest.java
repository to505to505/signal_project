package com.data_management;

import static org.junit.Assert.*;

import com.data_management.DataAccess;
import com.data_management.PatientRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DataAccessTest {

    private DataAccess dataAccess;
    private String testFilePath = "testData.json";
    private File testFile;

    @Before
    public void setUp() throws IOException {
        testFile = new File(testFilePath);
        dataAccess = new DataAccess(testFilePath);
    }

    @After
    public void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testGetData() throws IOException {
        createTestFile("{\"patients\":[{\"patientId\":1,\"metrics\":{\"HeartRate\":70.0},\"time stamp\":1609459200}]}");

        ArrayList<PatientRecord> records = dataAccess.getData();
        assertEquals(1, records.size());
        assertEquals(1, records.get(0).getPatientId());
        assertEquals(70.0, records.get(0).getMeasurementValue(), 0.01);
        assertEquals("HeartRate", records.get(0).getRecordType());
        assertEquals(1609459200L, records.get(0).getTimestamp());
    }

    @Test
    public void testDataConverter() {
        HashMap<String, Object> mockData = new HashMap<>();
        ArrayList<LinkedHashMap<String, Object>> patients = new ArrayList<>();

        LinkedHashMap<String, Object> patientRecord = new LinkedHashMap<>();
        patientRecord.put("patientId", 1);
        HashMap<String, Double> metrics = new HashMap<>();
        metrics.put("HeartRate", 70.0);
        patientRecord.put("metrics", metrics);
        patientRecord.put("time stamp", 1609459200);

        patients.add(patientRecord);
        mockData.put("patients", patients);

        ArrayList<PatientRecord> records = dataAccess.dataConverter(mockData);
        assertEquals(1, records.size());
        assertEquals(1, records.get(0).getPatientId());
        assertEquals(70.0, records.get(0).getMeasurementValue(), 0.01);
        assertEquals("HeartRate", records.get(0).getRecordType());
        assertEquals(1609459200L, records.get(0).getTimestamp());
    }

    @Test
    public void testIncorrectDataFormat() {
        HashMap<String, Object> mockData = new HashMap<>();
        ArrayList<LinkedHashMap<String, Object>> patients = new ArrayList<>();

        LinkedHashMap<String, Object> patientRecord = new LinkedHashMap<>();
        patientRecord.put("patientId", "incorrectId");
        HashMap<String, Double> metrics = new HashMap<>();
        metrics.put("HeartRate", 70.0);
        patientRecord.put("metrics", metrics);
        patientRecord.put("time stamp", 1609459200);

        patients.add(patientRecord);
        mockData.put("patients", patients);

        ArrayList<PatientRecord> records = dataAccess.dataConverter(mockData);
        assertEquals(0, records.size());
    }

    private void createTestFile(String content) throws IOException {
        FileWriter writer = new FileWriter(testFilePath);
        writer.write(content);
        writer.close();
    }
}
