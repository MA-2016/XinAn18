package com.mobileai.luncert.resolver;

import com.mobileai.luncert.utils.mulsession.MulSession;
import com.mobileai.luncert.utils.mulsession.MulSessionManager;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MulSessionResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MulSession.class);
    }

	@Override
	public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        
        MulSession mulSession = null;

        String newSessionId = (String)webRequest.getAttribute("MulSessionId", 0);
        
        mulSession = MulSessionManager.getSession(newSessionId);

        binderFactory.createBinder(webRequest, mulSession, parameter.getParameterName());
		return mulSession;
    }
    
}