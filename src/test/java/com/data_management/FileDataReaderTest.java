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
    private String testFilePath = "testData.txt";

    @Before
    public void setUp() throws IOException {
        dataStorage = new DataStorage();
        createTestFile();
        fileDataReader = new FileDataReader();
    }

    @Test
    public void testReadData() {
        fileDataReader.readData(testFilePath, dataStorage);
        List<PatientRecord> records = dataStorage.getRecords(1, 1609459200000L, 1609459200000L);
        assertEquals(1, records.size());
        assertEquals(98.6, records.get(0).getMeasurementValue(), 0.01);
        assertEquals("Temperature", records.get(0).getRecordType());
    }

    private void createTestFile() throws IOException {
        FileWriter writer = new FileWriter(testFilePath);
        writer.write("1,98.6,Temperature,1609459200000\n");
        writer.close();
    }
}
