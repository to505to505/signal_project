package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;

    public WebSocketOutputStrategy(int port) {
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        String message = formatMessage(patientId, timestamp, label, data);
        if (message != null) {
            // Broadcast the message to all connected clients
            for (WebSocket conn : server.getConnections()) {
                conn.send(message);
            }
        }
    }

    public static String formatMessage(int patientId, long timestamp, String label, String data) {
        // Validate the message fields
        if (patientId <= 0 || label == null || label.isEmpty() || timestamp <= 0 || data == null || data.isEmpty()) {
            System.err.println("Invalid message fields: patientId=" + patientId + ", timestamp=" + timestamp + ", label=" + label + ", data=" + data);
            return null;
        }

        // Return formatted message
        return String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
    }

    private static class SimpleWebSocketServer extends WebSocketServer {

        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}
