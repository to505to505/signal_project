package com.data_management;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.List;

public class WebSocketClientTest {

    private MockWebSocketServer mockServer;
    private WebSocketClient webSocketClient;
    private DataStorage dataStorage;
    private static final int TIMEOUT_MILLIS = 10000; // Increased timeout period

    @Before
    public void setUp() throws Exception {
        // Initialize DataStorage and WebSocketClient
        dataStorage = new DataStorage();
        webSocketClient = new WebSocketClient("ws://localhost:8080");

        // Start the mock WebSocket server and wait for it to be ready
        mockServer = new MockWebSocketServer(new InetSocketAddress("localhost", 8080));
        mockServer.start();
        if (!mockServer.waitForServerStart(TIMEOUT_MILLIS)) {
            throw new IllegalStateException("Server did not start in time");
        }
    }

    @After
    public void tearDown() throws Exception {
        // Stop the WebSocket client and server after each test
        if (webSocketClient != null) {
            webSocketClient = null;
        }
        if (mockServer != null) {
            mockServer.stop();
        }
    }

    @Test
    public void testReadData() throws Exception {
        // Connect the WebSocket client
        webSocketClient.connect();

        // Simulate sending data to the WebSocket server
        mockServer.sendMessage("1,98.6,Temperature,1609459200000");

        // Wait for the message to be processed
        webSocketClient.awaitMessage();

        // Verify the data was added to DataStorage
        List<PatientRecord> records = DataStorage.getInstance().getRecords(1, 1609459200000L, 1609459200000L);
        assertEquals(1, records.size());
        assertEquals(98.6, records.get(0).getMeasurementValue(), 0.01);
        assertEquals("Temperature", records.get(0).getRecordType());
    }
}
