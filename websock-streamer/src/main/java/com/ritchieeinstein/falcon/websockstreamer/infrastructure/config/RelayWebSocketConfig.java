package com.ritchieeinstein.falcon.websockstreamer.infrastructure.config;

import com.ritchieeinstein.falcon.websockstreamer.domain.service.WebSocketMessagingServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class RelayWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableStompBrokerRelay("/topic").setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest").setClientPasscode("guest");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/conn").setAllowedOrigins("*");
        registry.addEndpoint("/conn").withSockJS();
    }
}