package com.mobileai.luncert.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobileai.luncert.annotation.AdminAuthCheck;
import com.mobileai.luncert.utils.mulsession.MulSession;
import com.mobileai.luncert.utils.mulsession.MulSessionManager;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.sf.json.JSONObject;


@Component
public class AdminAuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HandlerMethod methodHandler = (HandlerMethod) handler;
        if (methodHandler.getMethodAnnotation(AdminAuthCheck.class) != null) {
            MulSession mulSession = MulSessionManager.getSession((String)request.getAttribute("MulSessionId"));
            if (mulSession.getValue("isAdmin", String.class) != null) return true;
            else {
                try (PrintWriter pw = response.getWriter()) {
                    JSONObject json = new JSONObject();
                    json.put("code", 199);
                    json.put("description", "reject access");
                    pw.write(json.toString());
                }
                return false;
            }
        } else return true;
    }
    
}