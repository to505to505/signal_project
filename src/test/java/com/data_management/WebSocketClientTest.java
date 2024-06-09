package com.data_management;

import static org.junit.Assert.*;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import com.data_management.WebSocketClient;
import org.junit.Before;
import org.junit.Test;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletionStage;

public class WebSocketClientTest {

    private MockWebSocketServer mockServer;
    private WebSocketClient webSocketClient;
    private DataStorage dataStorage;

    @Before
    public void setUp() throws Exception {
        // Initialize DataStorage and WebSocketClient
        dataStorage = new DataStorage();
        webSocketClient = new WebSocketClient("ws://localhost:8080");

        // Start the mock WebSocket server
        mockServer = new MockWebSocketServer(new URI("ws://localhost:8080"));
        mockServer.start();
    }

    @Test
    public void testReadData() throws Exception {
        // Simulate sending data to the WebSocket server
        mockServer.sendMessage("1,98.6,Temperature,1609459200000");

        // Read data from the WebSocket server
        webSocketClient.readData(dataStorage);

        // Give some time for data to be processed
        Thread.sleep(1000);

        // Verify the data was added to DataStorage
        List<PatientRecord> records = dataStorage.getRecords(1, 1609459200000L, 1609459200000L);
        assertEquals(1, records.size());
        assertEquals(98.6, records.get(0).getMeasurementValue(), 0.01);
        assertEquals("Temperature", records.get(0).getRecordType());
    }

    private class MockWebSocketServer extends Thread {
        private HttpClient client;
        private WebSocket server;
        private CountDownLatch latch = new CountDownLatch(1);
        private String message;

        public MockWebSocketServer(URI serverUri) {
            client = HttpClient.newHttpClient();
            client.newWebSocketBuilder().buildAsync(serverUri, new Listener() {
                @Override
                public void onOpen(WebSocket webSocket) {
                    server = webSocket;
                    latch.countDown();
                }

                @Override
                public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                    // Handle incoming messages here if needed
                    return null;
                }

                @Override
                public void onError(WebSocket webSocket, Throwable error) {
                    error.printStackTrace();
                }

                @Override
                public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
                    return null;
                }
            });
        }

        public void sendMessage(String message) throws InterruptedException {
            while (latch.getCount() > 0) {
                Thread.sleep(50); // Wait until the connection is open
            }
            this.message = message;
            if (server != null) {
                server.sendText(message, true);
            }
        }

        @Override
        public void run() {
            // Start the server logic here if needed
        }
    }
}
