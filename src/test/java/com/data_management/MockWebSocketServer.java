package com.data_management;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

public class MockWebSocketServer extends WebSocketServer {
    private final CountDownLatch latch = new CountDownLatch(1);

    public MockWebSocketServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Connection opened");
        latch.countDown();
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Connection closed");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message received: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully");
        latch.countDown();
    }

    public boolean waitForServerStart(long timeoutMillis) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (latch.getCount() > 0 && (System.currentTimeMillis() - startTime) < timeoutMillis) {
            Thread.sleep(50); // Wait until the connection is open
        }
        return latch.getCount() == 0;
    }

    public void sendMessage(String message) {
        System.out.println("Sending message: " + message);
        for (WebSocket conn : this.getConnections()) {
            conn.send(message);
        }
    }
}
