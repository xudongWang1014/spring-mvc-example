package com.wangxd.example.context.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
@Component
public class UserInterceptor extends HandlerInterceptorAdapter{
	
    private static final Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
	{	
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		
		boolean flag = true;

		//具体拦截逻辑

		return flag;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
		long endTime = System.currentTimeMillis();
		long startTime = (long) request.getAttribute("startTime");
		long time = endTime - startTime;
		logger.info("【接口】[{}],消耗时间[time]{}", request.getRequestURI(), time );
	}
}



