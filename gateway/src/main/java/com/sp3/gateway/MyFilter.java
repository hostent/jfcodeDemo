package com.sp3.gateway;

import java.util.HashSet;
import java.util.Set;

import com.jfcore.frame.Result;
import com.jfcore.web.GatewayFilter;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;


@WebFilter(filterName = "GatewayFilter", urlPatterns = "/api/v1/*")
public class MyFilter extends GatewayFilter {

	
	public static  Set<String> noLoginFilter = new HashSet< String>();
	
	
	@Override
	public Result<String> befour(ServletRequest request, String reqJson) {
		 
		return super.befour(request, reqJson);
	}
	
	@Override
	public Result<String> after(ServletRequest request, ServletResponse response, String body) {
		 
		return super.after(request, response, body);
	}
	
}
