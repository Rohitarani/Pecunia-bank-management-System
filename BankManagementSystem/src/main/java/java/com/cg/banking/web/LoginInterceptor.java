package com.cg.banking.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cg.banking.entity.Customer;
@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	@Qualifier("authenticatemap")
	private Map<String, Customer> authMap;
	
	private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse resp, Object handler)
			throws Exception {
		//HandlerInterceptor.super.preHandle(request, resp, handler);
		String token = request.getHeader("userId");
		Customer user = (Customer)authMap.get(token);
		logger.info("received tokenid " + request.getHeader("userId"));
		if(user != null)
		request.setAttribute("authFlag", true);
		else
			request.setAttribute("authFlag", false);
		 
		return true;
				 
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		//HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
		System.out.println("after completion");
	}

	

	
	
}
