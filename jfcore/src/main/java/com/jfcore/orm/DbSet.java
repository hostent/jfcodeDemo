package com.jfcore.orm;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jfcore.tools.PropertiesHelp;

public class DbSet<T> implements ISet<T>,IDbQuery<T> {
	
	
	ISet<T> set;
	IDbQuery<T> query;
	
	static String driver=null; 
	
	public DbSet(String connKey,Class<T> cls) {
		
		if(driver==null)
		{
			Properties pro =PropertiesHelp.getConf("jdbc-"+connKey);
			driver = pro.getProperty("driver");
		}
		
		
		switch (driver) {
		case "org.postgresql.Driver":
			
			DbSetPg<T> setPg = new DbSetPg<T>(connKey,cls);
			set=setPg;
			query = setPg;
			
			break;
		case "com.mysql.cj.jdbc.Driver":
			
			DbSetMy<T> setMy = new DbSetMy<T>(connKey,cls);
			set=setMy;
			query = setMy;

			break;

		default:
			break;
		}
 
 
	}

	public DbSet(String connKey, String prefix ,Class<T> cls) {
 
		Properties pro =PropertiesHelp.getConf("jdbc-"+connKey);
		String driver = pro.getProperty("driver");
		
		switch (driver) {
		case "org.postgresql.Driver":
			
			DbSetPg<T> setPg = new DbSetPg<T>(connKey,prefix,cls);
			set=setPg;
			query = setPg;
			
			break;
		case "com.mysql.cj.jdbc.Driver":
			
			DbSetMy<T> setMy = new DbSetMy<T>(connKey,prefix,cls);
			set=setMy;
			query = setMy;

			break;

		default:
			break;
		}
 
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
	public List<T> getList(List<Integer> ids) {
	 
		return query.getList(ids);
	}

	@Override
	public int delete(Object id) {
	 
		return set.delete(id);
	}

	@Override
	public int update(T t) {
	 
		return set.update(t);
	}

	@Override
	public Object add(T t) {
		 
		return set.add(t);
	}

	@Override
	public IDbQuery<T> where(String exp, Object... par) {
		 
		return query.where(exp, par);
	}

	@Override
	public IDbQuery<T> where(String exp, List<?> list) {
		 
		return query.where(exp, list);
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
	public IDbQuery<T> select(String... cols) {
		 
		return query.select(cols);
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
	public Map<String, Double> sum(String sumColum, String groupColum) {

		return query.sum(sumColum, groupColum);
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
	public long newId() {

		return query.newId();
	}

	@Override
	public int exec(String sql, Object... pars) {

		return set.exec(sql, pars);
	}

	@Override
	public String[] getCols(boolean isIncludeId) {
		return set.getCols(isIncludeId);
	}

	@Override
	public String getKey() {
		 
		return set.getKey();
	}

	@Override
	public String getTableName() {
		return set.getTableName();
	}

}
