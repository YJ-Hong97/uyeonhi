package com.kosta.uyeonhi.push;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket 
public class WebSocketConfig implements WebSocketConfigurer{
	
	private final NotificationHandler notificationHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(notificationHandler, "ws/noti").setAllowedOriginPatterns("*");
		
	}

}
