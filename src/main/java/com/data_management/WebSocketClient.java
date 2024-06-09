package com.data_management;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.io.IOException;

/**
 * WebSocket client for receiving real-time data and storing it in the data storage.
 * This class implements the {@link DataReader} interface to provide WebSocket-based data reading functionality.
 */
public class WebSocketClient implements DataReader {
    private WebSocket webSocket;
    private final String serverUri;
    private final CountDownLatch messageLatch = new CountDownLatch(1);
    private DataStorage dataStorage;

    /**
     * Constructs a WebSocketClient with the specified server URI.
     *
     * @param serverUri the URI of the WebSocket server
     */
    public WebSocketClient(String serverUri) {
        this.serverUri = serverUri;
    }

    /**
     * Connects to the WebSocket server and starts receiving data.
     *
     * @param dataStorage the storage where data will be stored
     */
    @Override
    public void readData(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        connect();
    }

    /**
     * Connects to the WebSocket server.
     */
    public void connect() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            webSocket = client.newWebSocketBuilder()
                    .buildAsync(new URI(serverUri), new WebSocketListener())
                    .join();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for a message to be received from the WebSocket server.
     *
     * @throws InterruptedException if the waiting is interrupted
     */
    public void awaitMessage() throws InterruptedException {
        messageLatch.await();
    }

    /**
     * Starts the WebSocket client. This method is a placeholder and is not used.
     */
    @Override
    public void start() {
        // Not needed for WebSocket-based data reader
    }

    /**
     * Stops the WebSocket client. This method is a placeholder and is not used.
     */
    @Override
    public void stop() {
        // Not needed for WebSocket-based data reader
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
            dataStorage = DataStorage.getInstance();
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
