package net.sf.minuteProject.configuration.bean;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;

public class Datasource {

	private static final String POSTGRES = "postgres";
	private static final String SQLSERVER = "sqlserver";
	private static final String ORACLE = "oracle";
	private static final String DB2 = "db2";
	private static final String DERBY = "derby";
	private static final String MYSQL = "mysql";
	private BasicDataSource basicDataSource;
	private String dbType;

	public Datasource(BasicDataSource basicDataSource, String dbType) {
		this.basicDataSource = basicDataSource;
		this.dbType = dbType.toLowerCase();
		convertDatasource();
	}

	private void convertDatasource() {
		String url = basicDataSource.getUrl();
		if (url!=null) {
			if (MYSQL.equals(dbType) ||
				DB2.equals(dbType) ||
				DERBY.equals(dbType)
				){
				//jdbc:hsqldb:hsql://{server-name}:${port-number}/{database-name}
				String urlAfterProtocol = StringUtils.substringAfter(url, "//");
				String urlServerPort = StringUtils.substringBefore(urlAfterProtocol, "/");
				server = StringUtils.substringBefore(urlServerPort, ":");
				port = StringUtils.substringAfter(urlServerPort, ":");
				databaseInstance = StringUtils.substringAfter(urlAfterProtocol, "/");
			} else
			if (SQLSERVER.equals(dbType) ){
				//jdbc:sqlserver://${server-name}:${port-number};databaseName=${database-name}
				String urlAfterProtocol = StringUtils.substringAfter(url, "//");
				String urlServerPort = StringUtils.substringBefore(urlAfterProtocol, ";");
				server = StringUtils.substringBefore(urlServerPort, ":");
				port = StringUtils.substringAfter(urlServerPort, ":");
				databaseInstance = StringUtils.substringAfter(urlAfterProtocol, "databaseName=");
			} else
			if (ORACLE.equals(dbType) ||
				POSTGRES.equals(dbType)  
					){
				//jdbc:hsqldb:hsql://{server-name}:${port-number}/{database-name}
				String urlAfterProtocol = StringUtils.substringAfter(url, (ORACLE.equals(dbType))?"@":"//");
				server = StringUtils.substringBefore(urlAfterProtocol, ":");
				String portAndSid = StringUtils.substringAfter(urlAfterProtocol, ":");
				port = StringUtils.substringBefore(portAndSid, ":");
				databaseInstance = StringUtils.substringAfter(portAndSid, ":");
			} 
		}
	}

	private String server, port, databaseInstance;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabaseInstance() {
		return databaseInstance;
	}

	public void setDatabaseInstance(String databaseInstance) {
		this.databaseInstance = databaseInstance;
	}

	public BasicDataSource getBasicDataSource() {
		return basicDataSource;
	}

}
