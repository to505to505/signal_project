package com.data_management;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

public class WebSocketClient {
    private WebSocket webSocket;
    private final String serverUri;
    private final CountDownLatch messageLatch = new CountDownLatch(1);
    private DataStorage dataStorage;

    public WebSocketClient(String serverUri) {
        this.serverUri = serverUri;
    }

    public void connect(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        try {
            HttpClient client = HttpClient.newHttpClient();
            webSocket = client.newWebSocketBuilder()
                    .buildAsync(new URI(serverUri), new WebSocketListener())
                    .join();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void awaitMessage() throws InterruptedException {
        messageLatch.await();
    }

    private class WebSocketListener implements Listener {
        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("Connected to WebSocket server");
            webSocket.request(1);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            String message = data.toString();
            System.out.println("Message received: " + message);
            processMessage(message);
            messageLatch.countDown();
            return Listener.super.onText(webSocket, data, last);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            error.printStackTrace();
        }

        private void processMessage(String message) {
            String[] parts = message.split(",");
            if (parts.length == 4) {
                try {
                    int patientId = Integer.parseInt(parts[0]);
                    double measurementValue = Double.parseDouble(parts[1]);
                    String recordType = parts[2];
                    long timestamp = Long.parseLong(parts[3]);
                    dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid data format: " + message);
                }
            } else {
                System.err.println("Incorrect data format: " + message);
            }
        }
    }
}
