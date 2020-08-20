package com.smart.example.config;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class CustomDefaultWebSessionManger extends DefaultWebSessionManager {
    public static final String AUTH_TOKEN = "token";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //从头部中获取sessionID
        if (request instanceof HttpServletRequest){
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String token = httpServletRequest.getHeader(AUTH_TOKEN);
            if (!StringUtils.isEmpty(token)){
                return token;
            }
        }
        //从cookie中获取sessionId
        return super.getSessionId(request, response);
    }
}
