package com.data_management;

import static org.junit.Assert.*;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.PatientRecord;
import org.junit.Before;
import org.junit.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileDataReaderTest {

    private DataStorage dataStorage;
    private FileDataReader fileDataReader;
    private String testFilePath = "dataFolder/";


    @Before
    public void setUp() throws IOException {
        dataStorage = new DataStorage();
        
        fileDataReader = new FileDataReader();
    }

    @Test
    public void testReadData() {
        
        DataStorage dataStorage = new DataStorage();
        fileDataReader.readData(testFilePath, dataStorage);
        Patient patient = dataStorage.getPatient(1);
        System.out.println(dataStorage.getPatient(1));
        PatientRecord record = patient.getRecordsLast(1, "Temperature").get(0);

       
        assertEquals(98.6, record.getMeasurementValue(), 0.01);
        assertEquals(1609459200000L, record.getTimestamp());
    }

    
}
