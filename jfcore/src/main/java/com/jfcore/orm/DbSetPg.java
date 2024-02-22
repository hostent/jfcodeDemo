package com.jfcore.orm;

import java.sql.Connection;
import java.util.*;

import com.jfcore.frame.CallerContext;
import com.jfcore.redis.CacheHelp;

 

public class DbSetPg<T> implements ISet<T>,IDbQuery<T> {

	public DbSetPg(String connKey,Class<T> cls) {
		_connKey = connKey;
		query = new DbQueryPg<T>(connKey, cls);
		_tEntity = new Entity<T>(cls);
	}

	public DbSetPg(String connKey, String prefix ,Class<T> cls) {
		_connKey = connKey;
		query = new DbQueryPg<T>(connKey, cls, prefix);
		_tEntity = new Entity<T>(cls,prefix);

	}
	

	private String _connKey;

	private DbQueryPg<T> query;
	private Entity<T> _tEntity;
	
 

	@Override
	public Object add(T t) {

		String sql = "insert into \"{table}\" ( {columns} ) values ( {values} );";  
																				 
		String[] columns;

		Object returnId = null;

		boolean isNeedId = false;

		if (_tEntity.isIdAuto) {
			isNeedId = false;
		} else {
			returnId = _tEntity.getIdValue(t);
			isNeedId = true;
		}

		columns = _tEntity.getColumns(true);

		sql = sql.replace("{table}",  _tEntity.tableName);

		sql = sql.replace("{columns}", "\"" + String.join("\",\"", columns) + "\"");

		sql = sql.replace("{values}", String.join(",", _tEntity.getColumnSymbol(columns)));
		
		
		if (!isNeedId) {
			returnId = (int)this.newId();			
			_tEntity.setIdValue(t, (int)returnId);	
		}

		
		if(CallerContext.isTran())
		{
			TranCommand.build(sql, _connKey, _tEntity.getColumnValues(true, t));	
		}
		else
		{
			try {
				Connection conn = MybatisUtils.getConn(_connKey);
				BaseSqlHelp.ExecSql(conn, sql, _tEntity.getColumnValues(true, t));
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			} finally {
				MybatisUtils.close();
			}		
		}
			
		query.resetCache();

		return returnId.toString();
	}

	@Override
	public int delete(Object id) {

		String sql = "delete from \"{table}\" where \"{key}\"=?";

		sql = sql.replace("{table}",  _tEntity.tableName);
		sql = sql.replace("{key}", _tEntity.key);

		Object[] par = new Object[1];
		par[0] = id;

	
		
		int result =0;
		
		
		if(CallerContext.isTran())
		{
			TranCommand.build(sql, _connKey, par);		
		}
		else
		{
			Connection conn = MybatisUtils.getConn(_connKey);
			try {
			
				result = BaseSqlHelp.ExecSql(conn, sql, par);
			}
			catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			finally {
				MybatisUtils.close();
			}
		}

		query.resetCache();

		return result;
	}

	@Override
	public int update(T t) {

		String sql = "update \"{table}\" set {updateStr} where {key}='{id}'";
		sql = sql.replace("{table}", _tEntity.tableName);

		String[] cols = _tEntity.getColumns(false);
		String colStr = "";
		for (int i = 0; i < cols.length; i++) {
			colStr = colStr + "," + String.format("\"%s\"=?", cols[i]);
		}
		colStr = colStr.substring(1, colStr.length());
		sql = sql.replace("{updateStr}", colStr);
		sql = sql.replace("{key}", _tEntity.key);

		sql = sql.replace("{id}", _tEntity.getIdValue(t).toString());

		Connection conn = MybatisUtils.getConn(_connKey);
		
		int result =0;
		
		if(CallerContext.isTran())
		{
			 TranCommand.build(sql, _connKey, _tEntity.getColumnValues(false, t));			
		}
		else
		{
			try {		
				
				result = BaseSqlHelp.ExecSql(conn, sql, _tEntity.getColumnValues(false, t));
				
			}catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			finally {
				MybatisUtils.close();
			}		
		}
		query.resetCache();

		return result;
	}


	@Override
	public IDbQuery<T> where(String exp, List<?> list) {
		 
		return query.where(exp, list);
	}

	
	@Override
	public IDbQuery<T> where(String exp, Object... par) {

		return query.where(exp, par);
	}

	@Override
	public IDbQuery<T> orderBy(String exp) {

		return query.orderBy(exp);
	}

	@Override
	public IDbQuery<T> orderByDesc(String exp) {

		return query.orderByDesc(exp);
	}

	@Override
	public IDbQuery<T> limit(int form, int length) {

		return query.limit(form, length);
	}

	@Override
	public IDbQuery<T> distinct() {

		return query.distinct();
	}

	@Override
	public T first() {

		return query.first();
	}

	@Override
	public List<T> toList() {

		return query.toList();
	}

	@Override
	public long count() {

		return query.count();
	}

	@Override
	public boolean exist() {

		return query.exist();
	}

	@Override
	public T get(Object id) {

		return query.get(id);
	}

	@Override
	public T getUnique(Object unique) {

		return query.getUnique(unique);
	}
	
	@Override
	public Map<String, Double> sum(String sumColum, String groupColum) {
		
		return query.sum(sumColum, groupColum);
	}
	
	@Override
	public IDbQuery<T> select(String... cols) {
		 
		return query.select(cols);
	}
	

	@Override
	public List<T> getList(List<Integer> ids) {
		return query.getList(ids);
	}
	
	
	@Override
	public int exec(String sql, Object... pars) {

		Connection conn = MybatisUtils.getConn(_connKey);

		int result = 0;
		try {
			result = BaseSqlHelp.ExecSql(conn, sql, pars);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			MybatisUtils.close();
		}

		query.resetCache();

		return result;

	}
	
	@Override
	public String[] getCols(boolean isIncludeId) {
		
		return _tEntity.getColumns(isIncludeId);
		
	}
	
	@Override
	public String getKey() {
		return _tEntity.key;
	}
	
	@Override
	public String getTableName() {
		 
		return _tEntity.tableName;
	}

	@Override
	public long newId() {
		return query.newId();

	}


 
	


}
