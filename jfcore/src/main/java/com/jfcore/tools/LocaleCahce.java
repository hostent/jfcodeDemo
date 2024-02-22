package com.jfcore.tools;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
 

 
 

public class LocaleCahce {
	
	
	public static enum LocaleCahceEnum {
		
		write,
		read

	}

	
	
	static HashMap<String, Cache<String, Object>> groupMap = new HashMap<String, Cache<String,Object>>();
	

	
	@SuppressWarnings("unchecked")
	public static <T> Cache<String, T> build(String group,LocaleCahceEnum localeCahceEnum,int size,int second)
	{
		
		if(!groupMap.containsKey(group))
		{
			Cache<String, T> caffeine=null;
			
			Caffeine<Object, Object> cache = Caffeine.newBuilder()				 
					.maximumSize(size);  //大小size 个，超过会产生覆盖行为	
			
			if(localeCahceEnum.equals(LocaleCahceEnum.read))
			{
				caffeine = cache.expireAfterAccess(second, TimeUnit.SECONDS).build();
			}
			else
			{
				caffeine = cache.expireAfterWrite(second, TimeUnit.SECONDS).build();
			}
					 
			
			groupMap.put(group, (Cache<String, Object>) caffeine);
			
			return caffeine;
		}
		else
		{
			return (Cache<String, T>) groupMap.get(group);
		}
		
		
		 
	}
	
 	

 
	
	public static void main(String[] args) throws InterruptedException {
		
		
		
		Cache<String, String> localeCahce= LocaleCahce.build("g1", LocaleCahceEnum.read, 110000, 5);
		
	
		
		 
			
		localeCahce.put("aaa"+23, String.valueOf(32));
			 
		 
 
		
		for (int i = 0; i < 100; i++) {
			
		 
			
		 
			Thread.sleep(11000);
			
			localeCahce.put("aaa"+23, String.valueOf(i));
 
			
		}
		 
		
		
	}
 
	


	

}
