package com.wangxd.example.context.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 */
public class WebFilter implements Filter {  
	
    private static final Logger logger = LoggerFactory.getLogger(WebFilter.class);
    
    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {  
        //Do nothing  
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {  
    	
    	   HttpServletRequest req = (HttpServletRequest) request;
    	
		   String url = request.getRemoteAddr();
		   int port = request.getRemotePort();
		   String origin = req.getHeader("Origin");
 		   String method = req.getMethod();
		   logger.info("过滤器请求url{},port{},origin{},method{}", url, port, origin, method);
    	   HttpServletResponse res = (HttpServletResponse) response;
        	
    	   if(StringUtils.isBlank(origin)){
    		   
    		   chain.doFilter(request, response);
    		   
    	   }else{
    		   if(origin.contains("xxxx")){
    			   logger.info("过滤器请求正常通过url{}", url);
        		   res.setHeader("Access-Control-Allow-Origin", origin);
        		   res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            	   res.setHeader("XDomainRequestAllowed","1");
            	   res.setHeader("Access-Control-Allow-Headers", "content-type,xxxx");
            	   chain.doFilter(request, response);
            	   
    		   }else{
    			   logger.info("过滤器请求被拦截url{}", url);
    			   res.setHeader("Access-Control-Allow-Origin", origin);
    			   chain.doFilter(request, response);
    		   }
    	   }
    }  
  
    @Override  
    public void destroy() {  
        //Do nothing  
    }  
  
}  