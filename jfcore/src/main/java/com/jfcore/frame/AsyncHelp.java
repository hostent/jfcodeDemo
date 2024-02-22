package com.jfcore.frame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
 

 

public class AsyncHelp {
	
	public static void main(String[] args) throws InterruptedException {
		
		
		for (int j = 0; j < 10; j++) {
			
			int tag =j;
			
 
			
			AsyncHelp.runAsync(()->
			{
				try {
					Thread.sleep(1*1000);
				} catch (InterruptedException e) {
					 
				}
				System.out.println("111:"+tag);
				
				int i=0;
				
				i = i/i;
				
				System.out.println("2222");
				 
			});
			
		}
		
		System.out.println("finish");
	
		Thread.sleep(50*1000);
	}
	
	private static Logger logger = LoggerFactory.getLogger(AsyncHelp.class);
	
	
	public static void runAsync(Runnable runnable)
	{
		String callid=CallerContext.getCallerID();
				 
		
		CompletableFuture.supplyAsync(() -> {
			
			CallerContext.setCallerID(callid);
			runnable.run();
			
		    return "";
		    
		}).whenComplete((result, e) -> {
			
		    if(e!=null)
		    {
		    	logger.error(e.getMessage(), e);
		    	    	
		    }
		    CallerContext.dispose();
		});
		

	}
	
 

}
