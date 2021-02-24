package net.sf.minuteproject.fitnesse.fixture;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.minuteproject.fitnesse.fixture.query.QueryOption;
import net.sf.minuteproject.model.db.Column;
import net.sf.minuteproject.utils.database.DatabaseUtils;
import net.sf.minuteproject.utils.query.QueryUtils;
import fit.ColumnFixture;

public abstract class DbInsertUpdateDeleteFixture extends ColumnFixture{

	Logger log = Logger.getLogger(this.getClass());
	
	public String insert() {
		QueryOption queryOption = new QueryOption();
		String query = QueryUtils.buildInsertStatement(getTable(), getColumns(), getColumnValue(), queryOption);
		log.debug("insert query = "+query);	
		return action(query);
	}
	
	public String insertExpectException() {
		QueryOption queryOption = new QueryOption();
		String query = QueryUtils.buildInsertStatement(getTable(), getColumns(), getColumnValue(), queryOption);
		log.debug("insert query = "+query);	
		return actionTranslateException(query);
	}
	
	public String insertWithTimeAsFunction() {
		QueryOption queryOption = new QueryOption();
		queryOption.setTimeAsFunction(true);
		String query = QueryUtils.buildInsertStatement(getTable(), getColumns(), getColumnValue(), queryOption);
		log.debug("insert query = "+query);
		return action(query);
	}
	
	public String update() {
		QueryOption queryOption = new QueryOption();
		String query = QueryUtils.buildUpdateStatement(getTable(), getColumns(), getColumnIndex(), getColumnValue(), getColumnWhereIndex(), getColumnWhereValue(), queryOption);
		log.debug("update query = "+query);
		return action(query);
	}
	
	public String updateWithTimeAsFunction() {
		QueryOption queryOption = new QueryOption();
		queryOption.setTimeAsFunction(true);
		String query = QueryUtils.buildUpdateStatement(getTable(), getColumns(), getColumnIndex(), getColumnValue(), getColumnWhereIndex(), getColumnWhereValue(), queryOption);
		log.debug("update query = "+query);
		return action(query);
	}
	
	public String updateExpectException() {
		String query = QueryUtils.buildUpdateStatement(getTable(), getColumns(), getColumnIndex(), getColumnValue(), getColumnWhereIndex(), getColumnWhereValue(), new QueryOption());
		log.debug("update query = "+query);
		return actionTranslateException(query);
	}
	
	public String delete() {
		String query = QueryUtils.buildDeleteStatement(getTable(), getColumns(), getColumnWhereIndex(), getColumnWhereValue(), new QueryOption());
		log.debug("delete query = "+query);
		return action(query);
	}
	
	public String deleteExpectException() {
		String query = QueryUtils.buildDeleteStatement(getTable(), getColumns(), getColumnWhereIndex(), getColumnWhereValue(), new QueryOption());
		log.debug("delete query = "+query);
		return actionTranslateException(query);
	}
	
	protected String action(String query) {
		try {
			performAction(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return (e.getMessage());
		} 
		return "OK";
	}
	
	protected String actionTranslateException(String query) {
		try {
			performAction(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return (translateException(e.getMessage()));
		} 
		return "OK";
	}

	protected String translateException(String message) {
		return StringUtils.substringBefore(message, ":");
	}

	private void performAction(String query) throws SQLException {
		Connection connection;
		connection = DatabaseUtils.getConnection();
		if (connection ==null)
			System.out.println("connection is null");
		Statement st = connection.createStatement();
		st.executeUpdate(query);
		connection.close();
	}
	
	protected abstract String getTable();
	
	protected abstract Map<Integer, Column> getColumns();
	
	protected abstract Map<Integer, String> getColumnIndex () ;
	
	protected abstract Map<String, String> getColumnValue () ;
	
	protected abstract Map<Integer, String> getColumnWhereIndex () ;
	
	protected abstract Map<String, String> getColumnWhereValue () ;

}
