package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;

import org.apache.commons.lang.StringUtils;

public class TestUtils {

	public String getTestPopulateFieldMethod (Column column, int number) {
		return getTestPopulateFieldMethod(column.getType(), column.getSize(), column.getScale(), number);
	}
	
	public String getTestPopulateFieldMethod (String dBType, String length, int scale, int number) {
		dBType = StringUtils.upperCase(dBType);
		if (dBType.equals("BOOLEAN"))
			return  "getBoolean"+number+"()";					
		if (dBType.equals("BIGINT"))
			return  "getLong"+number+"()";	
		if (dBType.equals("LONG"))
			return  "getLong"+number+"()";	
		if (dBType.equals("DOUBLE"))
			return  "getDouble"+number+"()";			
		if (dBType.equals("SMALLINT"))
			return  "getShort"+number+"()";			
		if (dBType.equals("REAL"))
			return  "getLong"+number+"()";			
		if (dBType.equals("INT"))
			return  "getInteger"+number+"()";		
		if (dBType.equals("TIME"))
			return  "getTimestamp ()";		
		if (dBType.equals("DECIMAL")) {
			if (scale==0)
				return "getLong"+number+"()";	
			else
				return  "getBigDecimal"+number+"()";
		}		
//		if (dBType.equals("DECIMAL"))
//			return  "getBigDecimal"+number+"()";
//		if (dBType.equals("SMALLINT"))
//			return  "getString"+number+"("+length+")";	
		if (dBType.equals("VARCHAR"))
			return  "getString"+number+"("+length+")";	
		if (dBType.equals("LONGVARCHAR"))
			return  "getString"+number+"("+length+")";	
		if (dBType.equals("VARCHAR2"))
			return  "getString"+number+"("+length+")";		
		if (dBType.equals("VARGRAPHIC"))
			return  "getString"+number+"("+length+")";			
		if (dBType.equals("CHAR"))
			return  "getString"+number+"("+length+")";		
		if (dBType.equals("INTEGER"))
			return  "getInteger"+number+"()";	
		if (dBType.equals("NUMERIC"))
			return  "getInteger"+number+"()";		
		if (dBType.equals("SMALLINT") || dBType.equals("TINYINT"))
			return  "getShort"+number+"(\""+length+"\")";		
		if (dBType.equals("DATE"))
			return  "getDate()";
		if (dBType.equals("TIMESTAMP"))
			return  "getTimestamp()";	
		if (dBType.equals("BLOB") )
			return  "getBlob("+length+")";	
		if (dBType.equals("BINARY"))
			return  "getBlob("+length+")";	
		if (dBType.equals("CLOB"))
			return  "getClob("+length+")";	
		if (dBType.equals("NVARCHAR2"))
			return  "getString"+number+"("+length+")";
		if (dBType.equals("BIT"))
			return  "getBoolean"+number+"()";		//TINYINT
//		if (dBType.equals("TINYINT"))
//			return  "getString"+number+"("+length+")";		
		if (dBType.equals("OTHER") || "LONGVARBINARY".equals(dBType))
			return  "getString"+number+"("+length+")";
		return ""+dBType+" - not converted";//retStr;			
	}
	
	public String getDate (Column column, boolean useTemporal) {
		if (column.getType().equals("TIMESTAMP") && useTemporal==false)
			return  "getTimestamp()";
		return  "getDate()";
	}

}
