package com.data_management;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

import com.cardio_generator.outputs.WebSocketOutputStrategy;

public class WebSocketClient implements DataReader {
    private DataStorage dataStorage;
    private WebSocket webSocket;
    private final String serverUri;
    private final CountDownLatch latch = new CountDownLatch(1);
    private static final int MAX_RETRIES = 3;

    public WebSocketClient(String serverUri) {
        this.serverUri = serverUri;
    }

    @Override
    public void readData(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        connectToWebSocket(0);
    }

    @Override
    public void start() {
        // Connection logic is handled in readData method
    }

    @Override
    public void stop() {
        if (webSocket != null) {
            webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Normal closure").thenRun(() -> {
                System.out.println("WebSocket closed");
                latch.countDown();
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void connectToWebSocket(int retryCount) {
        if (retryCount > MAX_RETRIES) {
            System.err.println("Max retries reached. Unable to connect to WebSocket server.");
            return;
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            this.webSocket = client.newWebSocketBuilder()
                    .buildAsync(new URI(serverUri), new WebSocketListener(retryCount))
                    .join();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private class WebSocketListener implements Listener {
        private int retryCount;

        public WebSocketListener(int retryCount) {
            this.retryCount = retryCount;
        }

        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("Connected to WebSocket server");
            webSocket.request(1);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            String rawMessage = data.toString();
            String[] parts = rawMessage.split(",");
            if (parts.length == 4) {
                try {
                    int patientId = Integer.parseInt(parts[0]);
                    double measurementValue = Double.parseDouble(parts[1]);
                    String recordType = parts[2];
                    long timestamp = Long.parseLong(parts[3]);

                    String formattedMessage = WebSocketOutputStrategy.formatMessage(patientId, timestamp, recordType, Double.toString(measurementValue));
                    if (formattedMessage != null) {
                        dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                    } else {
                        System.err.println("Received corrupted data message");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid data format: " + rawMessage);
                }
            } else {
                System.err.println("Incorrect data format: " + rawMessage);
            }

            webSocket.request(1);  // Request the next message
            return null;
        }

        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            System.out.println("Disconnected from WebSocket server");
            reconnectIfNecessary();
            return null;
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            error.printStackTrace();
            reconnectIfNecessary();
        }

        private void reconnectIfNecessary() {
            if (retryCount < MAX_RETRIES) {
                System.out.println("Attempting to reconnect... (" + (retryCount + 1) + ")");
                connectToWebSocket(retryCount + 1);
            } else {
                System.err.println("Max retries reached. Unable to reconnect to WebSocket server.");
            }
        }
    }
}
