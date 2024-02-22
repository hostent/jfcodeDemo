package com.jfcore.orm;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfcore.frame.CallerContext;
import com.jfcore.redis.CacheHelp;
import com.jfcore.tools.IdGenerater;

public class TranCommand {
	
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getConnkey() {
		return connkey;
	}

	public void setConnkey(String connkey) {
		this.connkey = connkey;
	}

	public List<Object> getPars() {
		return pars;
	}

	public void setPars(List<Object> pars) {
		this.pars = pars;
	}

	String sql;
	String connkey;
	
	List<Object> pars;
	
	public String toJson()
	{
		return JSONObject.toJSONString(this);
	}
	
	public void exec()
	{
		
		//MybatisUtils.beginTran(); //开启数据库事务
		Connection conn = MybatisUtils.getConn(this.getConnkey());
		BaseSqlHelp.ExecSql(conn, sql, pars.toArray());

	}
	
	
	public static void execList( List<TranCommand> commandList)
	{
		if(commandList==null || commandList.size()==0)
		{
			return;
		}
		MybatisUtils.beginTran();
		
		try {
		
			for (TranCommand tranCommand : commandList) {
				tranCommand.exec();
			}
			
			
			MybatisUtils.commit();
			
			CacheHelp.delete("TranConfirm:"+CallerContext.getServiceName()+"-"+CallerContext.getCallerID());
		
		} catch (Exception e) {
			e.printStackTrace();
			MybatisUtils.rollback();
		}
 	
	}
	
	
	public static List<TranCommand> getByCall()
	{
		
		List<TranCommand> list = new ArrayList<TranCommand>();
		
		
		for (int i = 0; i < 999; i++) {
			
			String json = CacheHelp.lpop("TranConfirm:"+CallerContext.getServiceName()+"-"+CallerContext.getCallerID());
			
			if(json==null)
			{
				break;
			}
			
			TranCommand tranCommand = TranCommand.build(json);
			
			list.add(tranCommand);
		}
		
		return list;
		
 
	}
	
	public static TranCommand build(String json)
	{
		return JSONObject.parseObject(json, TranCommand.class);
	}
	
	public static TranCommand build(String sql,String connkey,Object[] pars)
	{
		TranCommand tranCommand  = new TranCommand();
		tranCommand.setConnkey(connkey);
		tranCommand.setPars(Arrays.asList(pars) );
		tranCommand.setSql(sql);
 
		String key = "TranConfirm:"+CallerContext.getServiceName()+"-"+CallerContext.getCallerID();
		
		
		CacheHelp.rpush(key, tranCommand.toJson());
		//CacheHelp.hset(key, IdGenerater.newId(), tranCommand.toJson());
		CacheHelp.expire(key, 60); //20s
		
		return tranCommand;
		 
	}
	
 

}
