package com.mobileai.luncert.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobileai.luncert.utils.mulsession.MulSessionManager;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MulSessionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler) throws Exception {

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().compareTo("MulSessionId") == 0) {
                    if (!MulSessionManager.hasSession(cookie.getValue())) newSession(request, response);
                    else request.setAttribute("MulSessionId", cookie.getValue());
                    return true;
                }
            }
        }

        newSession(request, response);
        return true;
    }

    private void newSession(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = MulSessionManager.createSession();

        request.setAttribute("MulSessionId", sessionId);

        Cookie sessionCookie = new Cookie("MulSessionId", sessionId);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
    }
    
}