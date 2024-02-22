package com.jfcore.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.FIELD)
@Documented
public @interface Column {
		
	
	String name() default "";	
	
	String lable() default "";

	String format()  default "";	 
	
	 
	
}
