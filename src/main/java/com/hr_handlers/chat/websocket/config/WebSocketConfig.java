package com.hr_handlers.chat.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 접속 주소 url = ws://localhost:8080/ws
        registry.addEndpoint("/ws") // socket 연결 엔드포인트
                .setAllowedOrigins("*") // CORS 허용범위
                .withSockJS(); // 브라우저 호환성을 위해
        registry.addEndpoint("/ws") // 테스트용 연결 엔드포인트
                .setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // 메시지를 구독 요청하는 엔드 포인트(public, private)
        registry.setApplicationDestinationPrefixes("/app"); // 메시지를 발행하는 엔드 포인트
    }
}
