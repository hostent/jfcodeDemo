package com.jfcore.orm;

import java.sql.*;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.jfcore.frame.Result;
import com.jfcore.redis.CacheHelp;
import com.jfcore.redis.LastIdCache;
import com.jfcore.redis.RedisLock;
import com.jfcore.tools.Md5Help;

public class DbQueryPg<T> implements IQuery<T>, IDbQuery<T> {

	public DbQueryPg(String connKey, Class<T> type) {
		_connKey = connKey;
		_tEntity = new Entity<T>(type);
		trackSql = trackSql.replace("{table}", "\"" + _tEntity.tableName + "\"");
	}

	public DbQueryPg(String connKey, Class<T> type, String prefix) {
		_connKey = connKey;
		_tEntity = new Entity<T>(type, prefix);

		trackSql = trackSql.replace("{table}", "\"" + _tEntity.tableName + "\"");

	}

	Entity<T> _tEntity;

	private String _connKey;

	private String whereExp = "";
	private String orderExp;

	private String selectExp = "*";

	private String order = "";

	private Integer limitForm = null;
	private Integer limitLength = null;

	private String distinct = "";

	private String trackSql = "select {column} from {table} {where} {order} {limit}";

	private List<Object> sqlArgs = new ArrayList<Object>();

	@Override
	public IDbQuery<T> where(String exp, List<?> list) {

		if (list == null || list.size() == 0) {
			return this;
		}

		String str = "";
		for (Object item : list) {

			str = str + "'" + item + "',";

		}

		str = str.substring(0, str.length() - 1);

		exp = exp.replace("?", str);

		whereExp = whereExp + exp;
		return this;
	}

	public IDbQuery<T> where(String exp, Object... par) {

		whereExp = whereExp + exp;
		if (par != null && par.length > 0) {
			for (int i = 0; i < par.length; i++) {
				sqlArgs.add(par[i]);

			}
		}
		return this;
	}

	@Override
	public IDbQuery<T> orderBy(String exp) {
		orderExp = exp;
		order = "asc";

		return this;
	}

	@Override
	public IDbQuery<T> orderByDesc(String exp) {
		order = "desc";
		orderExp = exp;
		return this;
	}

	@Override
	public IDbQuery<T> limit(int form, int length) {
		limitForm = form;
		limitLength = length;
		return this;
	}

	@Override
	public IDbQuery<T> distinct() {
		this.distinct = "distinct";

		return this;
	}

	@Override
	public T first() {
		limitForm = 0;
		limitLength = 1;

		BuildColumns(null);
		BuildWhere(null, null);
		BuildOrder(null);
		BuildLimit(null);

		T resultObj = null;

		Connection conn = MybatisUtils.getConn(_connKey);

		PreparedStatement pst = null;
		try {
			pst = BaseSqlHelp.getPreparedStatement(conn, this.trackSql, sqlArgs.toArray());
			ResultSet rs = BaseSqlHelp.getResultSet(pst);

			resultObj = RecordMap.toEntity(_tEntity.getType(), rs);
			pst.close();
		} catch (SecurityException | SQLException e) {
			e.printStackTrace();
		} finally {
			BaseSqlHelp.closePst(pst);
			MybatisUtils.close();

		}

		return resultObj;
	}

	// 缓存只做list，单条查询，速度非常快，不需要缓存
	@Override
	public List<T> toList() {

		BuildColumns(null);
		BuildWhere(null, null);
		BuildOrder(null);
		BuildLimit(null);

		List<T> list = null;

		if (_tEntity.isCache) {
			list = getCacheList(this.trackSql);

			if (list != null) {
				return list;
			}
		}

		Connection conn = MybatisUtils.getConn(_connKey);

		PreparedStatement pst = null;
		try {
			pst = BaseSqlHelp.getPreparedStatement(conn, this.trackSql, sqlArgs.toArray());
			ResultSet rs = BaseSqlHelp.getResultSet(pst);
			list = RecordMap.toList(_tEntity.getType(), rs);

		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			BaseSqlHelp.closePst(pst);
			MybatisUtils.close();

		}

		if (_tEntity.isCache) {
			setCache(this.trackSql, list);
		}

		return list;
	}

	@Override
	public long count() {
		BuildColumns(" count(0) ");
		BuildWhere(null, null);
		BuildOrder(null);
		BuildLimit(null);

		Connection conn = MybatisUtils.getConn(_connKey);

		int count = 0;
		try {
			count = Integer.parseInt(BaseSqlHelp.ExecScalar(conn, this.trackSql, sqlArgs.toArray()).toString());
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		} finally {

			MybatisUtils.close();
		}

		return count;
	}

	@Override
	public boolean exist() {

		return count() > 0;
	}

	@Override
	public T get(Object id) {

		String whereStr = String.format(" where %s=? ", _tEntity.key);

		limitForm = 0;
		limitLength = 1;

		List<Object> arg = new ArrayList<Object>();
		arg.add(id);

		BuildColumns(null);
		BuildWhere(whereStr, arg.toArray());
		BuildOrder(" ");
		BuildLimit(" ");

		Connection conn = MybatisUtils.getConn(_connKey);

		T entity = null;
		PreparedStatement pst = null;
		try {
			pst = BaseSqlHelp.getPreparedStatement(conn, this.trackSql, sqlArgs.toArray());
			ResultSet rs = BaseSqlHelp.getResultSet(pst);
			entity = RecordMap.toEntity(_tEntity.getType(), rs);
			pst.close();
		} catch (SecurityException | SQLException e) {
			e.printStackTrace();
		} finally {
			BaseSqlHelp.closePst(pst);
			MybatisUtils.close();

		}

		return entity;
	}

	@Override
	public T getUnique(Object unique) {

		String whereStr = String.format(" where %s=? ", _tEntity.uniqueKey);

		limitForm = 0;
		limitLength = 1;

		List<Object> arg = new ArrayList<Object>();
		arg.add(unique);

		BuildColumns(null);
		BuildWhere(whereStr, arg.toArray());
		BuildOrder(" ");
		BuildLimit(" ");

		Connection conn = MybatisUtils.getConn(_connKey);
		T entity = null;

		PreparedStatement pst = null;
		try {

			pst = BaseSqlHelp.getPreparedStatement(conn, this.trackSql, sqlArgs.toArray());
			ResultSet rs = BaseSqlHelp.getResultSet(pst);
			entity = RecordMap.toEntity(_tEntity.getType(), rs);
			pst.close();
		} catch (SecurityException | SQLException e) {

			e.printStackTrace();
		} finally {
			BaseSqlHelp.closePst(pst);
			MybatisUtils.close();

		}

		return entity;
	}

	private void BuildColumns(String cols) {
		if (distinct.isEmpty() || distinct == null) {
			trackSql = trackSql.replace("{column}", distinct + " {column}");
		}

		if (cols != null && (!cols.isEmpty())) {
			trackSql = trackSql.replace("{column}", cols);
			return;
		}

//		String columnStr = "";
//	 
//		for (int i = 0; i < TEntity.columns.size(); i++) {
//					
//			String colName = TEntity.columns.get(i);
//
//			columnStr = columnStr + ",\"" + colName + "\"";
//		}
//
//		columnStr = columnStr.substring(1, columnStr.length());

		trackSql = trackSql.replace("{column}", selectExp);
	}

	private void BuildWhere(String where, Object[] args) {

		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				sqlArgs.add(args[i]);
			}
		}

		if (where != "" && where != null) {
			trackSql = trackSql.replace("{where}", where);
			return;
		}
		if (whereExp == "" || whereExp == null) {
			trackSql = trackSql.replace("{where}", "");
			return;
		}

		trackSql = trackSql.replace("{where}", " where " + whereExp);

	}

	private void BuildOrder(String order) {
		if (order != null) {
			trackSql = trackSql.replace("{order}", order);
			return;
		}
		if (orderExp == null) {
			trackSql = trackSql.replace("{order}", " order by 1 ");
			return;
		}

		trackSql = trackSql.replace("{order}", " order by " + orderExp + " " + this.order);

	}

	private void BuildLimit(String limit) {
		if (limit != null) {
			trackSql = trackSql.replace("{limit}", limit);
			return;
		}

		if (limitLength == null) {
			trackSql = trackSql.replace("{limit}", "");
			return;
		}

		if (limitForm == null) {
			limitForm = 0;
		}
		if (limitLength == null) {
			limitLength = 1;
		}
		trackSql = trackSql.replace("{limit}", String.format(" limit %s offset %s ", limitLength,limitForm));

	}

	@Override
	public Map<String, Double> sum(String sumColum, String groupColum) {
		BuildColumns(" sum(" + sumColum + ") as sumColum, " + groupColum + " as keyColum ");
		BuildWhere(null, null);
		BuildOrder("");
		BuildLimit(null);

		Connection conn = MybatisUtils.getConn(_connKey);

		Map<String, Double> result = null;
		PreparedStatement pst = null;
		try {
			pst = BaseSqlHelp.getPreparedStatement(conn, this.trackSql + " group by  " + groupColum, sqlArgs.toArray());
			result = RecordMap.toMap(BaseSqlHelp.getResultSet(pst));

			pst.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseSqlHelp.closePst(pst);
			MybatisUtils.close();

		}

		return result;
	}

	@Override
	public IDbQuery<T> select(String... cols) {
		for (String str : cols) {

			selectExp = selectExp + "," + str;

		}
		selectExp = selectExp.substring(1, selectExp.length());
		return this;
	}

	@Override
	public List<T> getList(List<Integer> ids) {
		if (ids == null || ids.size() == 0) {
			return null;
		}
		String str = "";

		for (Integer integer : ids) {
			if (integer != null) {
				str = str + "," + integer;
			}
		}
		if (str.isEmpty()) {
			return null;
		}

		str = str.substring(1, str.length());

		return this.where("id in (" + str + ")").toList();
	}

	// ---------- cache

	private String getCacheKey() {
		return "ormcache:" + _tEntity.getType();
	}

	public void resetCache() {
		CacheHelp.delete(getCacheKey());
	}

	void setCache(String sql, Object data) {
		if (data == null) {
			return;
		}

		String key = cacheItemKey(sql);

		CacheHelp.hset(getCacheKey(), key, JSONObject.toJSONString(data));

	}

	T getCache(String sql) {
		String key = cacheItemKey(sql);

		String json = CacheHelp.hget(getCacheKey(), key);

		if (json == null) {
			return null;
		}
		return JSONObject.parseObject(json, _tEntity.getType());

	}

	List<T> getCacheList(String sql) {
		String key = cacheItemKey(sql);

		String json = CacheHelp.hget(getCacheKey(), key);

		if (json == null) {
			return null;
		}

		return JSONObject.parseArray(json, _tEntity.getType());

	}

	String cacheItemKey(String sql) {
		StringBuffer stringBuffer = new StringBuffer(sql);
		String reverseSql = stringBuffer.reverse().toString();
		String key = Md5Help.toMD5(sql) + Md5Help.toMD5(reverseSql);
		return key;
	}

	@Override
	public long newId() {

		PreparedStatement pst = null;
		Connection conn = MybatisUtils.getConn(_connKey);

		try {
			pst = BaseSqlHelp.getPreparedStatement(conn, "SELECT nextval('seq_" + _tEntity.tableName + "'); ");
			ResultSet rs = BaseSqlHelp.getResultSet(pst);
			rs.next();

			return rs.getLong(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseSqlHelp.closePst(pst);
			MybatisUtils.close();
		}
		return 0;

	}

}
