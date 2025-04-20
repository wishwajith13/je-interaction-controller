package com.jeewaeducation.interaction_controller.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationDTO;
import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationSaveDTO;
import com.jeewaeducation.interaction_controller.repo.CounselorNotificationRepo;
import com.jeewaeducation.interaction_controller.service.CounselorNotificationService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final CounselorNotificationService counselorNotificationService;
    private final CounselorNotificationRepo counselorNotificationRepo;

    public NotificationWebSocketHandler(CounselorNotificationService counselorNotificationService, CounselorNotificationRepo counselorNotificationRepo) {
        this.counselorNotificationService = counselorNotificationService;
        this.counselorNotificationRepo = counselorNotificationRepo;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (session.getUri() == null || session.getUri().getPath() == null) {
            System.err.println("Session URI or path is null.");
            return;
        }
        String path = session.getUri().getPath();
        String[] parts = path.split("/");
        int counselorId;

        try {
            counselorId = Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid counselor ID in path: " + path);
            return;
        }

        sessions.put(counselorId, session);
        System.out.println("Counselor " + counselorId + " connected.");

        if (!counselorNotificationRepo.existsByCounselorIdAndSeenFalse(counselorId)) {
            return;
        }

        List<CounselorNotificationDTO> unseenNotifications = counselorNotificationService.getUnseenCounselorNotificationsById(counselorId);
        if (unseenNotifications.isEmpty()) {
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        for (CounselorNotificationDTO notification : unseenNotifications) {
            int studentId = notification.getStudentId();

            Map<String, Object> notificationData = new HashMap<>();
            notificationData.put("counselorId", counselorId);
            notificationData.put("studentId", studentId);
            notificationData.put("message", "Student " + studentId + " Assigned to you");

            String jsonResponse = objectMapper.writeValueAsString(notificationData);

            session.sendMessage(new TextMessage(jsonResponse));

            counselorNotificationService.markNotificationAsSeen(notification.getId());
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        System.out.println("Received message: " + message.getPayload());
        session.sendMessage(new TextMessage("Acknowledged: " + message.getPayload()));
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session,@NonNull CloseStatus status) {
        sessions.values().remove(session);
        System.out.println("Session closed: " + session.getId());
    }

    public void sendNotification(String notificationData) throws JsonProcessingException {
        Map<String, Object> notificationMsg = objectMapper.readValue(notificationData, new TypeReference<>() {});
        int counselorId = Integer.parseInt((String) notificationMsg.get("counselorId"));
        int studentId = Integer.parseInt((String) notificationMsg.get("studentId"));

        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("counselorId", counselorId);
        responseMessage.put("studentId", studentId);
        responseMessage.put("message", "Student " + studentId + " Assigned to you");

        String jsonResponse = objectMapper.writeValueAsString(responseMessage);

        WebSocketSession session = sessions.get(counselorId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(jsonResponse)); // Send JSON string
                System.out.println("Sent notification to counselor " + counselorId);
            } catch (Exception e) {
                System.err.println("Error sending message: " + e.getMessage());
            }
        } else {
            System.out.println("Counselor " + counselorId + " is not connected.");

            // Save the notification for later
            CounselorNotificationSaveDTO counselorNotificationSaveDTO = new CounselorNotificationSaveDTO();
            counselorNotificationSaveDTO.setCounselorId(counselorId);
            counselorNotificationSaveDTO.setStudentId(studentId);

            String saveCounselorNotification = counselorNotificationService.saveCounselorNotification(counselorNotificationSaveDTO);

            System.out.println("Notification saved: " + saveCounselorNotification);
        }
    }
}