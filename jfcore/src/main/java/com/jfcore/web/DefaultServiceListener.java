package com.jfcore.web;



import com.jfcore.web.ServiceListener;
 
 


public class DefaultServiceListener extends ServiceListener {
	
 
	
	public DefaultServiceListener(String... pageageName) {
		_packageName = pageageName;
	}
	
	String[] _packageName=null;
	
	public void defaultInit(ServiceBeanContainer hander) {
		
	}
	

	@Override
	public ServiceBeanContainer regist() {		 
		return new ServiceBeanContainer() {
			
			@Override
			public void init() {				
				defaultInit(this);
			}
			
			@Override
			public String[] getPackageName() {
 
				return _packageName;
			}
			
		};
	} 

}
