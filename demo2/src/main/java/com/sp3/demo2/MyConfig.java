package com.sp3.demo2;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.jfcore.web.DefaultServiceListener;
import com.jfcore.web.UserHandlerInterceptor;
import com.jfcore.web.WebConfig;

@Configuration
public class MyConfig  extends WebConfig{
	
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    
    @Bean
    public static DefaultServiceListener defaultServiceListener(){
    	
        return new DefaultServiceListener("com.sp3.facebean.service");
        
    }

	@Override
	public String getInterceptorPath() {
		 
		return "/**";
	}

	@Bean
	@Override
	public UserHandlerInterceptor getUserHandlerInterceptor() {
		return new UserHandlerInterceptor();
	}

}
