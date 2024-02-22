package com.jfcore.codeBuild;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class StarUmlBuild {
	
	public static void main(String[] args) {
		
		
		String str = readFileContent("D:\\model.txt");
		
		HashMap map = JSONObject.parseObject(str, HashMap.class);
		
		
		String tableNameStr = (String) ((Map<String, Object>)map.get("data")).get("name");
		
		//System.out.println(tableNameStr);
		
		
		String tableName = tableNameStr.split("   ")[1].trim();
		String tableDesc = tableNameStr.split("   ")[0].trim();
		
		
		List columns = ((List)((Map<String, Object>)map.get("data")).get("columns"));
		
		String columnsSql="";
		
		for (Object object : columns) {
			
			Map<String, Object>  itemMap =(Map<String, Object> )object;
			
			String colNameStr =(String) itemMap.get("name");
			String colType =(String) itemMap.get("type");
			
			String colName = colNameStr.split("   ")[1].trim();
			String colDesc = colNameStr.split("   ")[0].trim();
			
			if("id".equals(colName))
			{
				continue;
			}
			
			columnsSql = columnsSql+
					"		`"+colName+"` "+getColType(colType)+" COMMENT '"+colDesc+"', \n";
			
			//System.out.println(colName);
			
		}
	
		
		String sql =
				  "		SET NAMES utf8mb4; \n"
				+ "		SET FOREIGN_KEY_CHECKS = 0; \n"
				+ "		SET FOREIGN_KEY_CHECKS = 0; \n"
				+ "		DROP TABLE IF EXISTS `"+tableName+"`;\n"
				+ "		CREATE TABLE `"+tableName+"`  ( \n"
				+ "		`id` int(11) NOT NULL AUTO_INCREMENT, \n"
				+ columnsSql
				+ "		PRIMARY KEY (`id`) USING BTREE \n"
				+ "		) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC; \n"
				+ "		SET FOREIGN_KEY_CHECKS = 1; \n";

 
				
		System.out.println(sql);
		
		
		System.out.println("------------------------------------------\n\n\n");
		
		
		Entity en = new Entity();
		
		if(tableName.contains("_"))
		{
			en.setName(tableName.split("_")[1]);
		}
		else
		{
			en.setName(tableName);
		}
		
		
		List<Column> colList = new ArrayList<Column>();
		
		for (Object object : columns) {
			
			Map<String, Object>  itemMap =(Map<String, Object> )object;
			
			String colNameStr =(String) itemMap.get("name");
			String colType =(String) itemMap.get("type");
			
			String colName = colNameStr.split("   ")[1].trim();
			String colDesc = colNameStr.split("   ")[0].trim();
			
			Column col = new Column();
			 
			col.setColName(colName);
			
			String tt =getColType(colType);
			if(tt.contains("("))
			{
				tt=tt.split("\\(")[0];
			}
			
			col.setColType(tt);
			col.setNote(colDesc);
			colList.add(col);
			
		}
		en.setCols(colList);
		String code= en.getCode();
		
		System.out.println(code);
	}
	
	
	
 
	public static String getColType(String colType) {
		
		switch (colType) {
		case "int":
			return "int";
 
		case "varchar":
			return "String";
 
		case "string":
			return "varchar(255)";
			
		case "String":
			return "varchar(255)";
 
		case "datetime":
			return "datetime";
 
		case "date":
			return  "datetime";
 
		case "double":
			return "double";

		default:
			return "xx";
		}
		

	}
	
	
	
    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
            	 sbf.append("\n");
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
    

}
