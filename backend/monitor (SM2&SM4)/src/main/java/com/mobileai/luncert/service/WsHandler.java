package com.mobileai.luncert.service;

import java.io.IOException;

import com.mobileai.luncert.utils.Redis;
import com.mobileai.luncert.utils.SecurityTransport;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import redis.clients.jedis.JedisPubSub;

public class WsHandler extends TextWebSocketHandler {

    private class RedisPubSub extends JedisPubSub {
        
        @Override
        public void onMessage(String channel, String message) {
            try {
				sendMessage(message);
			} catch (IOException e) {
                logger.info(e);
                throw new RuntimeException(e);
			}
        }

    }

    private static Logger logger = Logger.getLogger(WsHandler.class);

    private WebSocketSession session;

    private Redis redis;

    private SecurityTransport st;

    public void sendMessage(String message) throws IOException {
        session.sendMessage(new TextMessage(st.parseSM4(message)));
    }

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        this.st = (SecurityTransport)session.getAttributes().get("st");
        redis = new Redis();
        redis.subscribe(new RedisPubSub(), "SecurityAnalyse");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        redis.close();
    }
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        logger.info(message);
        sendMessage("echo: " + message.getPayload());
    }
    
}