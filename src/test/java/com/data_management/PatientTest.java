package com.data_management;

import static org.junit.Assert.*;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class PatientTest {

    private Patient patient;

    @Before
    public void setUp() {
        patient = new Patient(1);
    }

    @Test
    public void testAddRecord() {
        patient.addRecord(98.6, "Temperature", 1609459200000L);
        List<PatientRecord> records = patient.getRecords(1609459200000L, 1609459200000L);
        assertEquals(1, records.size());
        assertEquals(98.6, records.get(0).getMeasurementValue(), 0.01);
        assertEquals("Temperature", records.get(0).getRecordType());
    }

    @Test
    public void testGetRecords() {
        patient.addRecord(98.6, "Temperature", 1609459200000L);
        patient.addRecord(99.1, "Temperature", 1609545600000L);
        List<PatientRecord> records = patient.getRecords(1609459200000L, 1609545600000L);
        assertEquals(2, records.size());
    }
}
