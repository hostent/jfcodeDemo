package com.jfcore.frame;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.jfcore.orm.MybatisUtils;
import com.jfcore.orm.TranCommand;
import com.jfcore.redis.CacheHelp;
import com.jfcore.tools.IdGenerater;
import com.jfcore.tools.PropertiesHelp;

public class CallerContext {
	
	
	public static String getServiceName()
	{
		try {
			return PropertiesHelp.getApplicationConf("spring.application.name");
		} catch (IOException e) {			 
			e.printStackTrace();
		}
		return null;
	}

	private static ThreadLocal<String> CallerID = new ThreadLocal<String>();

	private static ThreadLocal<Date> CallerTime = new ThreadLocal<Date>();

	private static ThreadLocal<HashMap<String, Object>> Tran = new ThreadLocal<HashMap<String, Object>>();

	public static Boolean isTran() {
		if (Tran.get() == null || (!Tran.get().containsKey("tran"))) {
			return false;
		}
		return true;
	}
	
	public static Boolean isRollback() {
		if (Tran.get() == null || (!Tran.get().containsKey("tran"))) {
			return false;
		}
		if("rollback".contentEquals(Tran.get().get("tran").toString()))
		{
			return true;
		}
		return false;
	}
	
	
	public static Boolean commit()
	{	
		
		TranCommand.execList(TranCommand.getByCall());
		
		return true;
	}

	public static void beginTran() {

		if (Tran.get() == null) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tran", "doing");
			Tran.set(map);
		}

	}

	public static void rollback() {
		if (!isTran()) {
			return;
		}
		HashMap<String, Object> map = Tran.get();
		map.put("tran", "rollback");		
		Tran.set(map);

		
		//调用undo

	}


	public static void clearTran() {
		Tran.remove();

	}

	public static String getCallerID() {

		String callerid = CallerID.get();
		if (callerid == null) {
			 
			CallerContext.setCallerID();
			return CallerID.get();
		}

		return callerid;

	}

	public static Date getBeginTime() {
		return CallerTime.get();
	}

	public static void setCallerID() {

		CallerID.set(IdGenerater.newId());
		CallerTime.set(new Date());

	}

	public static void setCallerID(String callId) {
		if (callId == null || callId.isEmpty()) {
			setCallerID();
		} else {
			CallerID.set(callId);
		}

		CallerTime.set(new Date());

	}

	public static void dispose() {
		CallerID.remove();

		CallerTime.remove();

		Tran.remove();

	}

}
