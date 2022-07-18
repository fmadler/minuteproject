package net.sf.minuteProject.utils;

import junit.framework.TestCase;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.*;

public class ConnectionTest extends TestCase{

	public void testIt () {
		try {
			ResultSet rs = getColumnsByFetch ("xxx");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public ResultSet getColumnsByFetch(String tableNamePattern) throws SQLException
    {

    	BasicDataSource ds = new BasicDataSource();
    	String url = "xxx";
    	String username = "xxx";
    	String password  = "xxx";
    	String driver = "xxx";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		Connection c = DriverManager.getConnection(url, username, password);
    	String query = "select * from "+tableNamePattern+" where 1 = 0";
    	Statement createStatement = c.createStatement();
		return createStatement.executeQuery(query);
    }
}
