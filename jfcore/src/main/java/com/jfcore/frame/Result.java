package com.jfcore.frame;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result<T> {
	
	private int status;
	
	private String message;
	
	private T data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
	public static <T> Result<T> get(int status,String message,T data)
	{
		Result<T> result = new Result<T>();
		result.setData(data);
		result.setMessage(message);
		result.setStatus(status);
		
		return result;
	}

	public static  <T> Result<T> get(int status,String message)
	{
		return get(status,message,null);
	}
	
	public static <T> Result<T> get(int status)
	{
		return get(status,null,null);
	}

	@JsonIgnore
	public Boolean isSucceed()
	{
		return getStatus()==1;
	}
	
	public static  <T> Result<T> succeed()
	{
		return get(1);
	}
	public static <T> Result<T> succeed(T data)
	{
		return get(1,"",data);
	}
	public static <T> Result<T> failure()
	{
		return get(-1);
	}
	public static  <T>  Result<T> failure(String message)
	{
		return get(-1,message);
	}
	public static  <T> Result<T> failure(int status, String message)
	{
		return get(status,message);
	}
	
	public static <T> Result<T> failure(int status,String message,T data)
	{
		return get(status,message,data);
	}
}
