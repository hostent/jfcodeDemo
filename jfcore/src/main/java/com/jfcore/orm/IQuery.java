package com.jfcore.orm;

import java.util.List;

public interface IQuery<T> {
	

    

    T get(Object id);

    T getUnique(Object unique);    
    
    List<T> getList(List<Integer> ids);
    
    
    
    
    

}
