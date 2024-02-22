package com.sp3.gateway;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.jfcore.web.DefaultServiceListener;


@Configuration
public class MyConfig {
	
	

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    
    @Bean
    public static DefaultServiceListener defaultServiceListener(){
    	
        return new DefaultServiceListener("com.sp3.facebean.service");
        
    }

}
