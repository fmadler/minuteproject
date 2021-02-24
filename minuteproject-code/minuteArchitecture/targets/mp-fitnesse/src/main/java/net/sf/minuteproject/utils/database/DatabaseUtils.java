package net.sf.minuteproject.utils.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import net.sf.minuteproject.fitnesse.fixture.DbConnectionFixture;

public class DatabaseUtils {

	private static BasicDataSource dataSource;
	
	public static Connection getConnection () throws SQLException {
		if (dataSource==null)
			dataSource = DbConnectionFixture.getDataSource();
		return dataSource.getConnection();
	}
	
}
