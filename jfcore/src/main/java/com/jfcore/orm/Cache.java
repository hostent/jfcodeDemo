package com.jfcore.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//缓存 一般用在 数据库查询缓慢的地方。比如大表。或者频率高，不占用数据库查询的地方。如果是小表单次速度不一定快。

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.TYPE)
@Documented
public @interface Cache {

	String key();
		
	 
	
}