package com.baidate.demo.config;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class IWebConfig extends HandlerInterceptorAdapter{

	@Resource
	private WebInterceptorInfo webInterceptorInfo;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return webInterceptorInfo.runMethod(handler,request,response);
    }
}
