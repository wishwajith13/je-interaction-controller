package com.jeewaeducation.interaction_controller.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeewaeducation.interaction_controller.config.NotificationWebSocketHandler;
import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationSaveDTO;
import com.jeewaeducation.interaction_controller.repo.CounselorNotificationRepo;
import com.jeewaeducation.interaction_controller.service.CounselorNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitMQListener {

    private final NotificationWebSocketHandler webSocketHandler;
    public RabbitMQListener(NotificationWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
    @Autowired
    private CounselorNotificationService counselorNotificationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "notification-queue")
    public void receiveNotification(String notificationData) {
        try {

            webSocketHandler.sendNotification(notificationData);

        } catch (Exception e) {
            System.err.println("Error while parsing notification: " + e.getMessage());
        }
    }

}