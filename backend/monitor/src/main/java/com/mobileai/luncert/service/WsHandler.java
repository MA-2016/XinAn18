package com.mobileai.luncert.service;

import java.io.IOException;

import com.mobileai.luncert.utils.Redis;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import redis.clients.jedis.JedisPubSub;

public class WsHandler extends TextWebSocketHandler {

    private static Logger logger = Logger.getLogger(WsHandler.class);

    private WebSocketSession session;

    private Redis redis;

    private Runnable redisSub;

    private JedisPubSub redisPubSub;

    public void sendMessage(String message) throws IOException {
        session.sendMessage(new TextMessage(message));
    }

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        redis = new Redis();
        redisPubSub = new JedisPubSub() {

            @Override
            public void onMessage(String channel, String message) {
                try {
                    sendMessage(message);
                } catch (IOException e) {
                    logger.info(e);
                    throw new RuntimeException(e);
                }
            }

        };
        redisSub = new Runnable(){

            @Override
            public void run() {
                redis.subscribe(redisPubSub, "SecurityAnalyse");
            }
            
        };
        redisSub.run();
    }
    
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        if(redisPubSub != null) {
            redisPubSub.unsubscribe();
            redisPubSub = null;
        }
        if(redis != null) {
            redis.close();
            redis = null;
        }
    }
    
}