package com.jfcore.orm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

 

public class HandlerMybatis implements InvocationHandler  {

	
	Object _obj=null;
	
	
	public HandlerMybatis(Object obj)
	{
		_obj = obj;
	}
	
	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {


		try {
			
			Object res = arg1.invoke(_obj, arg2);
			return res;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
			 
		}
		finally {
			MybatisUtils.close();
		}


	}

}
