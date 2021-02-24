package net.sf.minuteproject.fitnesse.fixture;

import java.sql.SQLException;

import net.sf.minuteproject.utils.query.QueryUtils;
import fitlibrary.DoFixture;

public class StatementFixture extends DoFixture {
	
	/**
	 * usage:
	 * |run statement | xx |returns|2|
	 */
	public boolean runStatementReturns(String statement, String result) throws SQLException {
		return result!=null?(runStatementQuery(statement).equals(result)):false;
	}
	/**
	 * usage:
	 * |check|run statement returns | xx |2|
	 */
	public String runStatementReturns(String statement) throws SQLException {
		return runStatementQuery(statement);
	}
	/**
	 * usage:
	 * |run statement | xx |
	 */
	public void runStatement(String statement) throws SQLException {
		QueryUtils.getVoidFromQueryQuery(statement);
	}

	private String runStatementQuery(String query) throws SQLException {
        return QueryUtils.getStringFromQueryQuery(query);		
	}

}
