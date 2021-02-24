package net.sf.minuteProject.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.apache.commons.dbcp.BasicDataSource;

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

//    	stmt = getConnection().createStatement();
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
		//return rs;
//		return getQueryParams(rs.getMetaData());    	
//    	return getMetaData().getColumns(getCatalog(), getSchemaPattern(), tableNamePattern, columnNamePattern);
    }
}
