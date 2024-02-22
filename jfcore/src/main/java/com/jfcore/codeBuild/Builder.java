package com.jfcore.codeBuild;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Builder {
	
	
	public static void main(String[] args) {
		
 
		String str = readFileContent("D:\\sql.txt");
		
		
		String name="p_pqc_inspection";

		String code = generateCode(str, name);
		
		System.out.println(code);

		
		
		
	}
	



	private static String generateCode(String str, String name) {
		Entity en = new Entity();
		en.setName(name);
		
		List<Column> colList = new ArrayList<Column>();
		for (String strItem : str.split("\n")) {
			
			if(strItem.isEmpty())
			{
				continue;
			}
			 
			 String[] tt = strItem.split("\t");
			 
			 Column col = new Column();
			 
			 col.setColName(tt[0]);
			 col.setColType(tt[1]);
			 if(tt.length>2)
			 {
				 col.setNote(tt[2]);
			 }

			 
			 colList.add(col);
			
		}
		en.setCols(colList);
		
		String code= en.getCode();
		return code;
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

	
	public static String getCode(String className)  
	{
		return className;
		 
		
		
	}

}
