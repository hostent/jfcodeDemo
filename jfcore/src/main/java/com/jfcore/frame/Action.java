package com.jfcore.frame;


@FunctionalInterface
public interface Action <T> {
	
	 T doWork();
}
