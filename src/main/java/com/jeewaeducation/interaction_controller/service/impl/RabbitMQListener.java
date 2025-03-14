package com.jeewaeducation.interaction_controller.service.impl;

import com.jeewaeducation.interaction_controller.config.NotificationWebSocketHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    private final NotificationWebSocketHandler webSocketHandler;
    public RabbitMQListener(NotificationWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
    @RabbitListener(queues = "notification-queue")
    public void receiveNotification(String notificationData) {
        try {
            webSocketHandler.sendNotification(notificationData);
        } catch (Exception e) {
            System.err.println("Error while parsing notification: " + e.getMessage());
        }
    }

}