package net.sf.minuteProject.plugin.propel;

import net.sf.minuteProject.configuration.bean.Datasource;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.plugin.php.PhpUtils;
import net.sf.minuteproject.model.db.type.FieldType;

public class PropelUtils extends PhpUtils{

	public static String getType(Column column) {
////		text: Type that maps an SQL CLOB to a PHP string.
		if (column.getType().equals(FieldType.VARCHAR.toString())||
			column.getType().equals(FieldType.VARCHAR2.toString())
		)
			return "VARCHAR";	
		if (column.getType().equals(FieldType.BIT.toString()))
			return "BOOLEAN";	
		return column.getType();
//		return PhpUtils.getType(column);
	}
	
	public static String getDsn(Model model) {
		String databaseType = getDatabaseType(model);
		Datasource datasource = new Datasource(model.getDataModel().getBasicDataSource(), databaseType);
		//TODO from the connection string
		if (databaseType!=null) {
			return databaseType+":host="+datasource.getServer()+";port="+datasource.getPort()+";dbname="+datasource.getDatabaseInstance();
//			if ("mysql".equals(databaseType))
//				return "mysql:host=localhost;dbname="+model.getName();
		}
		return "no DSN provide for DB type = "+databaseType;
	}


	
}
