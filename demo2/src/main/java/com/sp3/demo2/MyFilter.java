package com.sp3.demo2;

import com.jfcore.web.ServiceFilter;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(filterName = "GatewayFilter", urlPatterns = "*")
public class MyFilter extends ServiceFilter {

}
