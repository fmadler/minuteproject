package net.sf.minuteproject.fitnesse.fixture;

import java.sql.SQLException;

import net.sf.minuteproject.utils.query.QueryUtils;
import fitlibrary.DoFixture;

public class CountFixture extends DoFixture {
	
	/**
	 * usage:
	 * |number of elements of table |xxx|is|2|
	 */
	public boolean numberOfElementsOfTableIs(String table, int number) throws SQLException {
		return (count(table)==number);
	}

	private int count(String table) throws SQLException {
		String query = "select count(*) from "+table;
        return QueryUtils.getIntFromQueryQuery(query);		
	}
}
