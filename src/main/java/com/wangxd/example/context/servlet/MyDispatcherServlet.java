package com.wangxd.example.context.servlet;

import com.wangxd.example.base.BaseException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 自定义分发器
 * <pre>
 * 功能: 
 * 1.对请求method校验(get,post,delete,put)
 * 2.对url进行验证
 * </pre>
 */
public class MyDispatcherServlet extends DispatcherServlet implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 重写springmvc处理异常的方法,直接项目自定义的异常
	 */
	@Override
	protected ModelAndView processHandlerException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		Exception exception;
		if (ex instanceof HttpRequestMethodNotSupportedException) {
			// 拦截405异常
			exception = new BaseException(405, "请求method错误");
		} else {
			// 处理其他spring抛出的异常(500)或者SystemException(自定义)
			exception = ex;
		}
		return super.processHandlerException(request, response, handler, exception);
	}

	/**
	 * 重写校验url的功能(404) 因为spring对于404没有采用抛异常的方式
	 */
	@Override
	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		throw new BaseException(404,"请求【"+request.getRequestURL()+"】不存在");
	}

}