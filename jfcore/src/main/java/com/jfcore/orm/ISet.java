package com.jfcore.orm;

public interface ISet<T> extends ICommand<T>{
	
	int exec(String sql, Object... pars);
	
	String[] getCols(boolean isIncludeId);
	
	String getKey();
	
	String getTableName();
	
}
