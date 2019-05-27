package com.mobileai.luncert;

import java.util.Properties;

import com.github.pagehelper.PageHelper;
import com.mobileai.luncert.interceptor.AdminAuthInterceptor;
import com.mobileai.luncert.interceptor.AuthInterceptor;
import com.mobileai.luncert.interceptor.WsInterceptor;
import com.mobileai.luncert.service.WsHandler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import redis.clients.jedis.Jedis;

@MapperScan("com.mobileai.luncert.model.mysql")
@EnableWebMvc
@EnableWebSocket
@Configuration
public class Config implements WebMvcConfigurer, WebSocketConfigurer {

	// page helper
 
	@Bean
	public PageHelper getPageHelper(){
		PageHelper pageHelper=new PageHelper();
		Properties properties=new Properties();
		properties.setProperty("helperDialect","mysql");
		properties.setProperty("reasonable","true");
		properties.setProperty("supportMethodsArguments","true");
		properties.setProperty("params","count=countSql");
		pageHelper.setProperties(properties);
		return pageHelper;
	}

	// redis

	@Bean
	public Jedis jedis() {
		Jedis jedis = new Jedis("101.200.37.220");
		jedis.auth("Luncert428");
		return jedis;
	}

	// websocket
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(wsHandler(), "/websocket")
				.addInterceptors(new WsInterceptor())
				.addInterceptors(new HttpSessionHandshakeInterceptor())
				.setAllowedOrigins("*");
	}

	@Bean
	public WsHandler wsHandler() { return new WsHandler(); }

	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(8192);
		container.setMaxBinaryMessageBufferSize(8192);
		return container;
	}
	
	// auth interceptor

	@Bean
	public AdminAuthInterceptor adminAuthInterceptor() {
		// note spring doesn't response to manage customized Interceptor
		return new AdminAuthInterceptor();
	}

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(authInterceptor());
		registration.addPathPatterns("/event/**");

		registration = registry.addInterceptor(adminAuthInterceptor());
		registration.addPathPatterns("/event/**");
	}

}
