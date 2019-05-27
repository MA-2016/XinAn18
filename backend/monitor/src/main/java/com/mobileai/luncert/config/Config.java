package com.mobileai.luncert.config;

import java.util.List;
import java.util.Properties;

import com.github.pagehelper.PageHelper;
import com.mobileai.luncert.interceptor.AdminAuthInterceptor;
import com.mobileai.luncert.interceptor.AuthInterceptor;
import com.mobileai.luncert.interceptor.CORSInterceptor;
import com.mobileai.luncert.interceptor.MulSessionInterceptor;
import com.mobileai.luncert.interceptor.WsInterceptor;
import com.mobileai.luncert.resolver.MulSessionResolver;
import com.mobileai.luncert.service.WsHandler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@MapperScan("com.mobileai.luncert.model.mysql")
@EnableWebMvc
@EnableWebSocket
@Configuration
@AutoConfigureAfter(CorsConfig.class)
public class Config implements WebMvcConfigurer, WebSocketConfigurer {

	// mulsession

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new MulSessionResolver());
	}

	// page helper

	@Bean
	public PageHelper getPageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("helperDialect", "mysql");
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("params", "count=countSql");
		pageHelper.setProperties(properties);
		return pageHelper;
	}

	// websocket

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(wsHandler(), "/websocket").addInterceptors(new WsInterceptor())
				.addInterceptors(new HttpSessionHandshakeInterceptor()).setAllowedOrigins("*");
	}

	@Bean
	public WsHandler wsHandler() {
		return new WsHandler();
	}

	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(8192);
		container.setMaxBinaryMessageBufferSize(8192);
		return container;
	}

	@Bean
	public CORSInterceptor corsInterceptor() {
		return new CORSInterceptor();
	}

	// auth interceptor
	// note spring doesn't response to manage customized Interceptor

	@Bean
	public AdminAuthInterceptor adminAuthInterceptor() {
		return new AdminAuthInterceptor();
	}

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Bean
	public MulSessionInterceptor mulSessionInterceptor() {
		return new MulSessionInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(corsInterceptor());
		registration.addPathPatterns("/**");

		registration = registry.addInterceptor(mulSessionInterceptor());
		registration.addPathPatterns("/**");
		registration.order(0);

		registration = registry.addInterceptor(authInterceptor());
		registration.addPathPatterns("/event/**");
		registration.order(1);

		registration = registry.addInterceptor(adminAuthInterceptor());
		registration.addPathPatterns("/event/**");
		registration.order(1);
	}

}
