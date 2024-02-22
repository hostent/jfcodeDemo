package com.sp3.demo2.dataAccess;

import com.jfcore.orm.DbSet;
import com.sp3.demo2.dataAccess.tables.Box;

public class _db {
	
	
	static String getConnKey()
	{
		return "wms";
	}
	
	
	public static DbSet<Box> getBoxSet()
	{				
		return new DbSet<Box>(getConnKey(),Box.class);		 
	}

}
