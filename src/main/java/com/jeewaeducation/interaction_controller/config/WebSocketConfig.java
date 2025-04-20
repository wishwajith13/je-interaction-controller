    package com.jeewaeducation.interaction_controller.config;

    import com.jeewaeducation.interaction_controller.repo.CounselorNotificationRepo;
    import com.jeewaeducation.interaction_controller.service.CounselorNotificationService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.socket.config.annotation.EnableWebSocket;
    import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
    import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

    @Configuration
    @EnableWebSocket
    public class WebSocketConfig implements WebSocketConfigurer {

        @Autowired
        private CounselorNotificationService counselorNotificationService;
        @Autowired
        private CounselorNotificationRepo counselorNotificationRepo;

        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(new NotificationWebSocketHandler(counselorNotificationService, counselorNotificationRepo), "/ws/notifications/{counselorId}")
                    .setAllowedOrigins("*"); // Allow all origins for testing, restrict in production
        }
    }
