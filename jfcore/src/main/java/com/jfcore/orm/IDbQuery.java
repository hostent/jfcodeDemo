package com.jfcore.orm;

import java.util.List;
import java.util.Map;

 
public interface IDbQuery<T> extends IQuery<T> {

	IDbQuery<T> where(String exp,Object... par);
	
	IDbQuery<T> where(String exp,List<?> list);

	IDbQuery<T> orderBy(String exp);

	IDbQuery<T> orderByDesc(String exp);

	IDbQuery<T> limit(int form, int length);

	IDbQuery<T> distinct();
	
	IDbQuery<T> select(String... cols);

    T first();
    
    List<T> toList();
    
    
    Map<String,Double> sum(String sumColum,String groupColum);

    long count();

    boolean exist();
    
    long newId();
    
//    <E> E  FirstSelect(Class<E> cla);
//    
//    <E> List<E> ToListSelect(Class<E> cla);
//    
    
    
 
    
    
}
