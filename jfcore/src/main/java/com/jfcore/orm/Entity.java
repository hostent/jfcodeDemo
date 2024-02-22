package com.jfcore.orm;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.jfcore.frame.Cache;


public class Entity<T> {
	
	//static
	
	private static Map<String, Map<String, Method>> entityMap  =new ConcurrentHashMap<String, Map<String,Method>>();
	
	private static ReentrantLock lock = new ReentrantLock();
	
	
	// 字段名 -- get方法
	public static <T> Map<String, Method> entityGetMap(Class<T> cls)
	{
		Map<String, Method> map=null;
		
		String key=cls.getName()+"-get";
		
		if(!entityMap.containsKey(key))
		{
 
			lock.lock();
			try {
				if(!entityMap.containsKey(key))
				{
					
					map = new ConcurrentHashMap<String, Method>();
					
					for (Field field : cls.getDeclaredFields()) {
						
						if(field.isAnnotationPresent(Column.class))
						{	
							Column column = field.getAnnotation(Column.class);
							Method[] methods =cls.getMethods();
							
							for (Method method : methods) {
								
								if(method.getName().toLowerCase().equals("get"+field.getName().toLowerCase()))
								{
									method.setAccessible(true);
									map.put(column.name(), method);
								}
								
							}
										
						}
						
					}				
					
					entityMap.put(key, map);
						
				}
				else {
					map=entityMap.get(key);
				}

				
			} finally {
				lock.unlock();
			}
		}
		else
		{
			map = entityMap.get(key);
		}		
		
		return map;
		
	}
	// 字段名 -- set方法
	public static  <T> Map<String, Method> entitySetMap(Class<T> cls)
	{
		Map<String, Method> map=null;
		
		String key=cls.getName()+"-set";
		
		if(!entityMap.containsKey(key))
		{
 
			lock.lock();
			try {
				
				if(!entityMap.containsKey(key))
				{					
				
					map = new ConcurrentHashMap<String, Method>();
					
					for (Field field : cls.getDeclaredFields()) {
						
						if(field.isAnnotationPresent(Column.class))
						{
							Column column = field.getAnnotation(Column.class);
							Method[] methods =cls.getMethods();
							
							for (Method method : methods) {
								
								if(method.getName().toLowerCase().equals("set"+field.getName().toLowerCase()))
								{
									method.setAccessible(true);
									map.put(column.name(), method);
								}
								
							}
										
						}
						
					}				
					
					entityMap.put(key, map);
				}
				else
				{
					map = entityMap.get(key);
				}
				
			} finally {
				lock.unlock();
			}
		}
		else
		{
			map = entityMap.get(key);
		}		
		
		return map;
		
	}
	
	
	
	
	// Entity
	
	public Entity(Class<T> type)
	{
		_type=type;
		
		init();
	}
	
	public Entity(Class<T> type,String fix)
	{
		_type=type;
		
		init();
		
		tableName =tableName.replace("{fix}", fix);
	}
	
	Class<T> _type=null;
	
	public String tableName="";
	public String key="";
	public String uniqueKey="";
	public Boolean isIdAuto=false;
	public Boolean isCache=false;
	
	
	public List<String> columns = new ArrayList<String>();
	
	private void init()
	{
		Table table=  this.getType().getAnnotation(Table.class);
		if(table!=null)
		{
			tableName=table.name();
			key = table.key();
			uniqueKey =  table.uniqueKey();
		}	
		
		IdAuto identity=  this.getType().getAnnotation(IdAuto.class);
		if(identity!=null)
		{
			isIdAuto =true;
		}
		
		Cache cache=  this.getType().getAnnotation(Cache.class);
		if(cache!=null)
		{
			isCache =true;
		}
		
		
		Map<String, Method> map =Entity.entityGetMap(_type);
		
		for (String str : map.keySet()) {
			columns.add(str);
		}
 
	}

	public Class<T> getType() {		 

		return _type;

	} 
	

	
	public String[] getColumnSymbol(String[] columns) 
	{
		String[] result = new String[columns.length];
		for (int i = 0; i < columns.length; i++) {		 
			
			result[i]="?";
			
		}
		return result;
	}
	
	public  Object getIdValue(T t)
	{
		
		Method method = Entity.entityGetMap(_type).get(key);
		
		try {
			return method.invoke(t, null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
				 
	}
	
	public  Object setIdValue(T t,int id)
	{
		
		Method method = Entity.entitySetMap(_type).get(key);
		
		try {
			return method.invoke(t, id);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
				 
	}
	
	public Object[] getColumnValues(boolean isIncludeId, T t) {
		
		List<Object> list = new ArrayList<Object>();
		
		Map<String, Method> map = Entity.entityGetMap(_type);
		for (String keyv : map.keySet()) {
			if((!isIncludeId) && keyv.equals(key))
			{
				 continue;
			}
			try {
				list.add(map.get(keyv).invoke(t, null));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
 
				e.printStackTrace();
			}
			
		}	 
	 
		return list.toArray();

	}

	public String[]  getColumns(boolean isIncludeId)
	{
		List<String> temp = new ArrayList<String>();
		
		for (String str : columns) {
			if((!isIncludeId) && str.equals(key))
			{
				 continue;
			}
			temp.add(str);
		}
		
		return temp.toArray(new String[temp.size()]);
	}
	
}
