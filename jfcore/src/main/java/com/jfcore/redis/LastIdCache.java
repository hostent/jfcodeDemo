package com.jfcore.redis;

import redis.clients.jedis.Jedis;

public class LastIdCache {

	// 内存锁
	public static long newId(String connkey, String tableName) {

		Jedis jedis = RedisUtil.getJedis();

		long res = newIdLoop(connkey, tableName, 50, jedis);

		jedis.close();

		return res;

	}

	private static long newIdLoop(String connkey, String tableName, int loop, Jedis jedis) {
		Long res = 0L;

		String nxKey = "nx:" + connkey + "-" + tableName;

		Long nxtag = jedis.setnx(nxKey, "1");

		if (nxtag == 1) {
			jedis.expire(nxKey, 10);
			String key = "LastId:" + connkey;

			String idStr = jedis.hget(key, tableName);

			if (idStr != null && !idStr.equals("nil")) {
				res = Long.valueOf(idStr) + 1;
				jedis.hset(key, tableName, res.toString());
			}

			jedis.del(nxKey);
		} else {
			if (loop < 0) {
				return 0;
			}
			loop--;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("递归");
			// 递归
			return newIdLoop(connkey, tableName, loop, jedis);

		}

		return res;
	}

	public static void cacheId(String connkey, String tableName, Long newId) {
		Jedis jedis = RedisUtil.getJedis();
		String key = "LastId:" + connkey;
		jedis.hset(key, tableName, newId.toString());

		jedis.close();

	}

}
