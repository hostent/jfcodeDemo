package com.jfcore.orm;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class RecordMap {

	static Object parse(Type paramType, ResultSet rs, String key) throws SQLException {
		
		

		if (rs == null) {
			return null;
		}

		Object t = null;

		// Integer
		if (paramType == Integer.class || paramType == int.class) {
			t = rs.getInt(key);
			return t;
		}
		// String
		else if (paramType == String.class) {
			t = rs.getString(key);
			return t;
		}

		// Date
		else if (paramType == Date.class) {
			if(rs.getTimestamp(key)==null )
			{
				return null;
			}
			t =  new Date(rs.getTimestamp(key).getTime());
			return t;
		}

		// Float
		else if (paramType == Float.class || paramType == float.class) {
			t = rs.getFloat(key);
			return t;
		}
		// Double
		else if (paramType == Double.class || paramType == double.class) {
			t = rs.getDouble(key);
			return t;
		}
		// Decimal
		else if (paramType == BigDecimal.class) {
			t = rs.getBigDecimal(key);
			return t;
		}
		// Boolean
		else if (paramType == Boolean.class || paramType == boolean.class) {
			t = rs.getBoolean(key);
			return t;
		}
		// Byte
		else if (paramType == Byte.class || paramType == byte.class) {
			t = rs.getByte(key);
			return t;
		}
		// Long
		else if (paramType == Long.class || paramType == Long.class) {
			t = rs.getLong(key);
			return t;
		}		
		// StringBuffer
		else if (paramType == StringBuffer.class) {
			return new StringBuffer(rs.getString(key));
		}

		return t;

	}


	static Object parse1(Type paramType, ResultSet rs, String key ,SimpleDateFormat sdf) throws SQLException {

		if (rs == null) {
			return null;
		}		 

		Object t = rs.getObject(key);
		if(t==null)
		{
			return null;
		}

		// Integer
		if (paramType == Integer.class || paramType == int.class) {
			return Integer.parseInt(t.toString());
			 
		}
		// String
		else if (paramType == String.class) {
			return t.toString();
			 
		}

		// Date
		else if (paramType == Date.class) {
			
			if(t.getClass().equals(java.util.Date.class))
			{
				return t;
			}
			else
			{
				if(t==null || t.toString().isEmpty())
				{
					return null;
				}				 
				try {
					return sdf.parse(t.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
	 
		}

		// Float
		else if (paramType == Float.class || paramType == float.class) {
			return Float.parseFloat(t.toString());	
		}
		// Double
		else if (paramType == Double.class || paramType == double.class) {
			return Double.parseDouble(t.toString());			 
		}
		// Decimal
		else if (paramType == BigDecimal.class) {
			return new BigDecimal(t.toString());			 
		}
		// Boolean
		else if (paramType == Boolean.class || paramType == boolean.class) {			
			return Boolean.parseBoolean(t.toString()); 
		}
		// Byte
		else if (paramType == Byte.class || paramType == byte.class) {
			return Byte.parseByte(t.toString());
		}
		// Long
		else if (paramType == Long.class || paramType == Long.class) {
			return Long.parseLong(t.toString());	
		}

		return t;

	}

	
	
	public static <T> List<T> toList(Class<T> clazz, ResultSet rs) {

		List<T> list = new ArrayList<T>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


		try {

			if (rs == null) {
				return null;
			}

			Map<String, Method> mmap = Entity.entitySetMap(clazz);

			while (rs.next()) {

				T t = (T) clazz.newInstance();

				for (String key : mmap.keySet()) {

					try {
						rs.findColumn(key); // 列不存在，就异常
					} catch (Exception e) {
						continue;
					}

					Method f = mmap.get(key);

					Object value = parse1(f.getParameters()[0].getType(), rs, key,sdf);

					Object[] arr = new Object[1];
					arr[0] = value;
					f.invoke(t, arr);

				}

				list.add(t);
			}
			if (!rs.isClosed()) {
				rs.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public static <T> T toEntity(Class<T> clazz, ResultSet rs) {

		try {

			if (rs == null) {
				return null;
			}

			Map<String, Method> mmap = Entity.entitySetMap(clazz);

			while (rs.next()) {

				T t = (T) clazz.newInstance();

				for (String key : mmap.keySet()) {

					try {
						rs.findColumn(key); // 列不存在，就异常
					} catch (Exception e) {
						continue;
					}

					Method f = mmap.get(key);
					Object value = parse(f.getParameters()[0].getType(), rs, key);
					Object[] arr = new Object[1];
					arr[0] = value;
					f.invoke(t, arr);

				}

				if (!rs.isClosed()) {
					rs.close();
				}

				return t;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Map<String, Double> toMap(ResultSet rs) throws SQLException {
		Map<String, Double> map = new ConcurrentHashMap<String, Double>();
		if (rs == null) {
			return null;
		}

		while (rs.next()) {

			String key = rs.getString("keyColum".toUpperCase());// DataConverter.parse(String.class,
																// rs.getObject("keyColum".toUpperCase()));
			Double value = rs.getDouble("sumColum".toUpperCase());// DataConverter.parse(Double.class,
																	// rs.getObject("sumColum".toUpperCase()));

			map.put(key, value);

		}

		if (!rs.isClosed()) {
			rs.close();
		}

		return map;

	}
}
