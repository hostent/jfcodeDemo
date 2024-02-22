package com.jfcore.codeBuild;

 
import java.util.Date;
import java.util.List;

import com.jfcore.orm.IdAuto;
 

public class Entity {
	
	private String name;
	private String nameBig;
	
	private List<Column> cols;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		
		nameBig = name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
	}

	public List<Column> getCols() {
		return cols;
	}

	public void setCols(List<Column> cols) {
		this.cols = cols;
	}


	
	public String getCode()
	{
		String format =""
				+ "\r\nimport com.jfcore.orm.Column;"
				+ "\r\nimport com.jfcore.orm.IdAuto;"
				+ "\r\nimport com.jfcore.orm.Table;"
				+ "\r\n"
				+ "\r\nimport lombok.Getter;"
				+ "\r\nimport lombok.Setter;"
				+ "\r\n"
				+ "\r\n@Getter @Setter"
				+ "\r\n@IdAuto"
				+ "\r\n@Table (name=\"{name}\",key=\"id\", uniqueKey = \"xxx\")"
				+ "\r\npublic class {nameBig}  {"
				+ "\r\n"
				+ "\r\n{cols}}";
		
		
		
		
		String colStr = "";
		
		for (Column column : cols) {
			colStr+="\r\n"+column.getCode();
		}
		
		String str = format.replace("{name}", name).replace("{nameBig}", nameBig).replace("{cols}", colStr);
		
		
		return str;
		
		
	}

 
	
/*	private void tt()
	{
		for (String key : cols.keySet()) {
			cols.get(key);
		}
	}*/

}
