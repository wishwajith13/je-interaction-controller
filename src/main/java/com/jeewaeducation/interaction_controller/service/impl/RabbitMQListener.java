package com.jeewaeducation.interaction_controller.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    @RabbitListener(queues = "notification-queue")
    public void receiveNotification(String message) {
        System.out.println("Received notification: " + message);

        // Send notification via WebSocket (implement WebSocket logic here)
    }
}
