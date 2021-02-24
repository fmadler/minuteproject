package net.sf.minuteProject.plugin.hibernate;

import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;

public class Hibernate3 {
	
	public static String getMany2ManyReadOnly(Table table) {
		return "read-only";
	}
	
	public static String getDialect (Database database) {
		if (database==null) {
			return "database type must be set";
		}
		String type = database.getType();
		if (type==null) {
			type = database.getDataModel().getDatabaseType();
			if (type==null) {
				return "database type must be set";
			}
		}
		if (type.equals("ORACLE")) 
			return "org.hibernate.dialect.Oracle10gDialect";
		else if (type.equals("HSQLDB"))
			return "org.hibernate.dialect.HSQLDialect";
		else if (type.equals("MYSQL"))
			return "org.hibernate.dialect.MySQLDialect";		
		else if (type.equals("POSTGRESQL"))
			return "org.hibernate.dialect.PostgreSQLDialect";		
		else if (type.equals("DERBY"))
			return "org.hibernate.dialect.DerbyDialect";			
		else if (type.equals("MSSQL"))
			return "org.hibernate.dialect.SQLServer2008Dialect";			
		return "no dialect for database type = "+type;
	}

	public static String getDialect () {
		return "wrongDialect";
	}

}
