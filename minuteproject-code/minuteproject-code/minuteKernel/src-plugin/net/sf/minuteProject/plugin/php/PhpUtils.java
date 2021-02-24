package net.sf.minuteProject.plugin.php;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.java.JavaUtils;
import net.sf.minuteproject.model.db.type.FieldType;

public class PhpUtils {

	public static String getType(Column column) {
		//http://docs.doctrine-project.org/en/2.0.x/reference/basic-mapping.html
//		text: Type that maps an SQL CLOB to a PHP string.
		if (column.getType().equals(FieldType.CLOB.toString()))
				return "text";	
//		string: Type that maps an SQL VARCHAR to a PHP string.
		if (ColumnUtils.isString(column))
			return "string";
//		integer: Type that maps an SQL INT to a PHP integer.
		if (ColumnUtils.isInteger(column))
			return "integer";
//		smallint: Type that maps a database SMALLINT to a PHP integer.
		if (column.getType().equals(FieldType.SMALLINT.toString()))
			return "smallint";
//		bigint: Type that maps a database BIGINT to a PHP string.
		if (column.getType().equals(FieldType.BIGINT.toString()))
			return "bigint";
//		boolean: Type that maps an SQL boolean to a PHP boolean.
		if (ColumnUtils.isBoolean(column))
			return "boolean";
//		decimal: Type that maps an SQL DECIMAL to a PHP double.
		if (column.getType().equals(FieldType.DECIMAL.toString()))
			return "decimal";
//		date: Type that maps an SQL DATETIME to a PHP DateTime object.
		if (column.getType().equals(FieldType.DATE.toString()))
			return "date";		
//		time: Type that maps an SQL TIME to a PHP DateTime object.
		if (column.getType().equals(FieldType.TIME.toString()))
			return "time";			
//		datetime: Type that maps an SQL DATETIME/TIMESTAMP to a PHP DateTime object.
		if (column.getType().equals(FieldType.DATETIME.toString()) ||
			column.getType().equals(FieldType.TIMESTAMP.toString())	)
			return "datetime";	
//		object: Type that maps a SQL CLOB to a PHP object using serialize() and unserialize()
		//TODO not inline with the above spec
		if (column.getType().equals(FieldType.BLOB.toString()))
			return "object";	
//		array: Type that maps a SQL CLOB to a PHP object using serialize() and unserialize()
//		float: Type that maps a SQL Float (Double Precision) to a PHP double. IMPORTANT: Works only with locale settings that use decimal points as separator.
		if (column.getType().equals(FieldType.DOUBLE.toString()))
			return "float";			
		return "TOIMPLEMENT";
	}
	
	public static String getPackageName (Model model, Template template, Table table) {
		String result = CommonUtils.getPackageName(model, template, table);
		return FormatUtils.transformSlashIntoBackSlash(FormatUtils.getDirFromPackage(result));
	}
	
	public String getVariableNaming (String columnVar) {
		return JavaUtils.getJavaVariableNaming(columnVar);
	}
	
	public String getClassNaming (String columnVar) {
		return JavaUtils.getJavaClassNaming(columnVar);
	}
	
	public static String getDatabaseType(Model model) {
		String dbType = model.getDataModel().getDatabase().getType().toLowerCase();
		if (dbType.equals("hsqldb"))
			return "mysql";
		return dbType;
	}
	
	public String getDbName(Model model) {
		return model.getName();//TODO false to change
	}
//	public static String getDatabaseType(Model model) {
//		return PhpUtils.getDatabaseName(model);
//	}
	
}
