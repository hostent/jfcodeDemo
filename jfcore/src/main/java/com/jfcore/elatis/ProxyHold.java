package com.jfcore.elatis;

import java.lang.reflect.Proxy;

 

public class ProxyHold {
	
	@SuppressWarnings("unchecked")
	public static <T> T getProxyMapper(Class<T> tClass) {
		
		return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new HanderEngine(tClass));
	}

}
