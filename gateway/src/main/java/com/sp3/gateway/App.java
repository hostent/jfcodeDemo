package com.sp3.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@ServletComponentScan
@EnableDiscoveryClient  
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication sa= new SpringApplication(App.class);    	
    	sa.run(args);
    }
}
