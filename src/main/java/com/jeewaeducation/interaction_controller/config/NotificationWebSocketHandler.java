package com.jeewaeducation.interaction_controller.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    private static final Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extract counselorId from URL
        String path = session.getUri().getPath();
        String[] parts = path.split("/");
        int counselorId = Integer.parseInt(parts[parts.length - 1]);

        sessions.put(counselorId, session);
        System.out.println("Counselor " + counselorId + " connected.");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
        session.sendMessage(new TextMessage("Acknowledged: " + message.getPayload()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
        System.out.println("Session closed: " + session.getId());
    }

    public void sendNotification(int counselorId, String message) {
        WebSocketSession session = sessions.get(counselorId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
                System.out.println("Sent notification to counselor " + counselorId);
            } catch (Exception e) {
                System.err.println("Error sending message: " + e.getMessage());
            }
        } else {
            System.out.println("Counselor " + counselorId + " is not connected.");
        }
    }
}