package com.jfcore.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

 
import redis.clients.jedis.Jedis;

public class CacheHelp {
	
	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "EX";
	
	
 
     //temp cache
	 public static String get(String key)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 String result = jedis.get(key);
		 jedis.close();
		 return result;
	 }

	public static String incr(String key)
	{
		Jedis jedis =  RedisUtil.getJedis();
		Long result = jedis.incr(key);
		jedis.close();
		return result.toString();
	}

	 public static HashMap<String, String> mget(String... key)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
 
		 List<String> result = jedis.mget(key);
		 jedis.close();
		 
		 HashMap<String, String> hm = new HashMap<String, String>();
		 
		 for (int i = 0; i < key.length; i++) {
			 if(result.get(i)!=null && result.get(i).equals("nil"))
			 {
				 result.set(i, null);
			 }
			 hm.put(key[i], result.get(i));
		 }
		 
		 return hm;
	 }
	 
	 public static void set(String key,String val)
	 {
		 Jedis jedis =  RedisUtil.getJedis();	
		
		 jedis.set(key, val);
		 jedis.close();
	 }
	 
	 public static Set<String> keys(String pattern)
	 {
		 Jedis jedis =  RedisUtil.getJedis();			 
		 Set<String> result = jedis.keys(pattern);		 
		 jedis.close();
		 return  result;
	 }
	 
	 public static Map<String, Boolean> setNXBatch(Map<String, String> map,int expireSecond)
	 {		 
		 Map<String, Boolean> mapResult = new HashMap<String, Boolean>();
		 
		 Jedis jedis =  RedisUtil.getJedis();	
		 

		 for (String key : map.keySet()) {
			 
			 String result =jedis.setex(key, expireSecond, map.get(key));//.set(key, map.get(key), SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireSecond);

			 if (LOCK_SUCCESS.equals(result)) {
				 mapResult.put(key, true);
			 }
			 else
			 {
				 mapResult.put(key, false);
			 }
				
		 }
		 
		 jedis.close();
		 
		 return mapResult;
		
 
	 }
	 
	 public static Boolean setNX(String key,String val,int expireSecond)
	 {
		 Jedis jedis =  RedisUtil.getJedis();		 
		 String result =jedis.setex(key,expireSecond, val);
		 jedis.close();
		 
		 if (LOCK_SUCCESS.equals(result)) {
				return true;
		 }
		 return false;
	 }
	 public static Boolean setNX(String key,String val)
	 {
		 Jedis jedis =  RedisUtil.getJedis();		 
		 Long result =jedis.setnx(key, val);
		 jedis.close();
		 
		 if (result.equals(1)) {
				return true;
		 }
		 return false;
	 }
	 
	 public static void set(String key,String val,int expireSecond)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 jedis.set(key, val);
		 jedis.expire(key, expireSecond);
		 jedis.close();
	 }
	 
	 public static void delete(String key)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 jedis.del(key);
		 jedis.close();
	 }
 
	 
	 public static void expire(String key,int expireSecond)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 jedis.expire(key,expireSecond);
		 jedis.close();
	 }
	 
	 public static void pexpire(String key,long milliseconds)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 jedis.pexpire(key, milliseconds);
		 jedis.close();
	 }
	 
	 //hash
	 public static Long hset(String key,String fieldId,String val)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 Long result = jedis.hset(key, fieldId, val);
		 jedis.close();		 
		 return  result;
	 }
	 
	 public static Long hlen(String key)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 Long result = jedis.hlen(key);
		 jedis.close();		 
		 return  result;
	 }
	 
	 public static String hget(String key,String field)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 String result = jedis.hget(key, field);
		 jedis.close();
		 return result;
	 }
	 public static long hdel(String key,String field)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 long result = jedis.hdel(key, field).longValue();
		 jedis.close();
		 return result;
	 }
	 public static Map<String, String> hget(String key)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 Map<String, String> result = jedis.hgetAll(key);
		 jedis.close();
		 return result;
	 }
	 public static void hmset(String key,Map<String,String> hash)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 jedis.hmset(key, hash);
		 jedis.close();
	 }
	 public static void hdelAll(String key)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 jedis.hdel(key);
		 jedis.close();
	 }
	 public static List<String> hmget(String key,String... fieldIds)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 List<String> result = jedis.hmget(key, fieldIds);
		 jedis.close();
		 return result;
	 }
	 
	 //队列
	 public static void rpush(String key,String val)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 jedis.rpush(key, val);
		 jedis.close();
	 }
	 public static void rpush(String key,String... val)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 jedis.rpush(key, val);
		 jedis.close();
	 }
	 
	 public static String rpop(String key)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 String result =jedis.rpop(key);
		 jedis.close();
		 if(result.equals("nil"))
		 {
			 return null;
		 }
		 return result;
	 }
	 
	 public static Long llen(String key)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 Long result =jedis.llen(key);
		 jedis.close();		  	
		 return result;
	 }
	 
	 public static String lpop(String key)
	 {
		 Jedis jedis =  RedisUtil.getJedis();
		 String result =jedis.lpop(key);
		 jedis.close();
		 if(result==null)
		 {
			 return null;
		 }
		 if(result.equals("nil"))
		 {
			 return null;
		 }		
		 return result;
	 }


	public static List<String> lGetList(String key)
	{
		Jedis jedis =  RedisUtil.getJedis();
		List<String> result = jedis.lrange(key, 0, -1);
		jedis.close();
		return result;
	}
	 
	 
	 
}
