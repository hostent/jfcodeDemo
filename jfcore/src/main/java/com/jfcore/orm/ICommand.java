package com.jfcore.orm;

public interface ICommand<T> {
	
    int delete(Object id);

    int update(T t);

	Object add(T t);
}
