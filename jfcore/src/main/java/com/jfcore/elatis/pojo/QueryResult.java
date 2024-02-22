package com.jfcore.elatis.pojo;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
 

public class QueryResult<T> {
	
	
	String _index;
	String _type;
	String _id;
	T _source;
	

	public String get_index() {
		return _index;
	}
	public void set_index(String _index) {
		this._index = _index;
	}
	public String get_type() {
		return _type;
	}
	public void set_type(String _type) {
		this._type = _type;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public T get_source() {
		return _source;
	}
	public void set_source(T _source) {
		this._source = _source;
	}
	
	
	public static <T> T getData(TypeReference<T> type,String json)
	{
		if(json==null)
		{
			return null;
		}
		
		HashMap<String, Object> hashMap = JSONObject.parseObject(json, HashMap.class);
		if(hashMap==null || !hashMap.containsKey("hits"))
		{
			return null;
		}
		
		HashMap<String, Object> hitsMap = JSONObject.parseObject(hashMap.get("hits").toString(), HashMap.class);
		if(hitsMap==null || !hashMap.containsKey("hits"))
		{
			return null;
		}
		String hitsStr = hitsMap.get("hits").toString();
		
		
		T queryResult =JSONObject.parseObject(hitsStr, type );
		

		return queryResult;
		
	}
	
	
	
}
