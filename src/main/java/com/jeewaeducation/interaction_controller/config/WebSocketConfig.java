    package com.jeewaeducation.interaction_controller.config;

    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.socket.config.annotation.EnableWebSocket;
    import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
    import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

    @Configuration
    @EnableWebSocket
    public class WebSocketConfig implements WebSocketConfigurer {

        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(new NotificationWebSocketHandler(), "/ws/notifications/{counselorId}")
                    .setAllowedOrigins("*"); // Allow all origins for testing, restrict in production
        }
    }
