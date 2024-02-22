package com.jfcore.codeBuild;



public class Column {
	
	private String colName;
	private String colType;
	
	private String colNameBig;
		
	private String note="";
	
 
	
	public String getCode()
	{
		
		String template ="\t/*   {key}   {note}   */"
				+ "\r\n\t@Column(name = \"{key}\",lable = \"{note}\")"
				+ "\r\n\tprivate {coltype} {key};"
				+ "\r\n";
			
		 
		
		return template.replace("{key}", colName).replace("{note}", note).replace("{keybig}", colNameBig).replace("{coltype}", colType);
		 
	}
	
	
 
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		
		this.colName = colName;
		
		colNameBig = colName.substring(0, 1).toUpperCase()+colName.substring(1, colName.length());
	}
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		
		switch (colType) {
		case "int":
			this.colType="Integer";
			break;
		case "varchar":
			this.colType="String";
			break;
		case "string":
			this.colType="String";
			break;
		case "datetime":
			this.colType="Date";
			break;
		case "date":
			this.colType="Date";
			break;
		case "double":
			this.colType="Double";
			break;

		default:
			this.colType="xxx";
		}
		

	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
