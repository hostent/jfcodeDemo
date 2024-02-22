package com.jfcore.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.TYPE)
@Documented
public @interface Table {
		
	
	String name() default "";	
	
	String key() default "";
	
	String uniqueKey() default "";
	

	 
	
}
