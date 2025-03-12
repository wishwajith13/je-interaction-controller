package com.jeewaeducation.interaction_controller.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationDTO;
import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationSaveDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorNotification;
import com.jeewaeducation.interaction_controller.repo.CounselorNotificationRepo;
import com.jeewaeducation.interaction_controller.service.CounselorNotificationService;
import com.jeewaeducation.interaction_controller.service.impl.CounselorNotificationServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
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
        // Extract counselorId from URL
        String path = session.getUri().getPath();
        String[] parts = path.split("/");
        int counselorId = Integer.parseInt(parts[parts.length - 1]);

        sessions.put(counselorId, session);
        System.out.println("Counselor " + counselorId + " connected.");

        if(!counselorNotificationRepo.existsByCounselorId(counselorId)) {
            return;
        }

        List<CounselorNotificationDTO> unseenNotifications = counselorNotificationService.getUnseenCounselorNotificationsById(counselorId);
        if(unseenNotifications.isEmpty()) {
            return;
        }

        for (CounselorNotificationDTO notification : unseenNotifications) {
            session.sendMessage(new TextMessage(notification.getMessage()));
            counselorNotificationService.markNotificationAsSeen(counselorId); // Mark as seen
        }
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

    public void sendNotification(String notificationData) throws JsonProcessingException {

        Map<String, Object> notificationMsg = objectMapper.readValue(notificationData, Map.class);

        int counselorId = Integer.parseInt((String) notificationMsg.get("counselorId"));
        int studentId = Integer.parseInt((String) notificationMsg.get("studentId"));
        String message = "student " + studentId + " Assigned to you";

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
            CounselorNotificationSaveDTO counselorNotificationSaveDTO = new CounselorNotificationSaveDTO();
            counselorNotificationSaveDTO.setCounselorId(counselorId);
            counselorNotificationSaveDTO.setMessage(message);
            counselorNotificationSaveDTO.setStudentId(studentId);

            String saveCounselorNotification = counselorNotificationService.saveCounselorNotification(counselorNotificationSaveDTO);

            System.out.println("Notification saved: " + saveCounselorNotification);
        }
    }
}