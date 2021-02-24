package net.sf.minuteproject.fitnesse.fixture;

import org.apache.commons.dbcp.BasicDataSource;

import fit.ColumnFixture;

public class DbConnectionFixture extends ColumnFixture{

	public static String username, password, url, driverClassName;
	
	private static BasicDataSource dataSource;

	public static BasicDataSource getDataSource() {	
		if (username!=null && password!=null && url!=null && driverClassName!=null) {
			dataSource = new BasicDataSource();
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setUrl(url);
			dataSource.setDriverClassName(driverClassName);
		}
		return dataSource;
	}
	
}
