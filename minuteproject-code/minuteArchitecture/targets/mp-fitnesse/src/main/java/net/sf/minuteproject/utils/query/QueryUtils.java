package net.sf.minuteproject.utils.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.minuteproject.fitnesse.fixture.enumeration.SelectOption;
import net.sf.minuteproject.fitnesse.fixture.query.QueryOption;
import net.sf.minuteproject.model.db.Column;
import net.sf.minuteproject.model.db.type.FieldType;
import net.sf.minuteproject.utils.ParserUtils;
import net.sf.minuteproject.utils.database.DatabaseUtils;

public class QueryUtils {

	private static final Logger _logger = Logger.getLogger(QueryUtils.class);
	public static String buildInsertStatement (
			String table,
			Map<Integer, Column> columns,
			Map<String, String>  columnValue,
			QueryOption queryOption) {
		StringBuffer sb = new StringBuffer();
		sb.append(getInsert(table, columns, columnValue));
		sb.append(getInsertValue(columns, columnValue, queryOption));
		return sb.toString();
	}
	
	public static String buildUpdateStatement (
			String table,
			Map<Integer, Column> columns,
			Map<Integer, String> columnIndex,
			Map<String, String>  columnValue,
			Map<Integer, String> columnWhereIndex,
			Map<String, String>  columnWhereValue,
			QueryOption queryOption) {
		StringBuffer sb = new StringBuffer();
		sb.append(getUpdate(table, columns, columnIndex, columnValue, queryOption));
		sb.append(getWhereQueryCRUD(columns, columnWhereIndex, columnWhereValue, queryOption));		
		return sb.toString();
	}
	
	public static String buildDeleteStatement (
			String table,
			Map<Integer, Column> columns,
			Map<Integer, String> columnWhereIndex,
			Map<String, String>  columnWhereValue,
			QueryOption queryOption) {
		StringBuffer sb = new StringBuffer();
		sb.append(getDelete(table));
		sb.append(getWhereQueryCRUD(columns, columnWhereIndex, columnWhereValue, queryOption));		
		return sb.toString();
	}
	
	private static String getDelete(String table) {
		return "DELETE FROM "+table+" ";	
	}
	
	private static String getUpdate(String table, Map<Integer, Column> columns, Map<Integer, String>  columnIndex, Map<String, String>  columnValue, QueryOption queryOption) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE "+table+" ");
		sb.append(getUpdateSetQuery(columns, columnIndex, columnValue, queryOption));
		sb.append(" ");
		return sb.toString();		
	}	

	private static String getInsert(String table, Map<Integer, Column>  columns, Map<String, String>  columnValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO "+table+" (");
		sb.append(getColumnQuery2(columns, columnValue));
		sb.append(") ");
		return sb.toString();		
	}
	
	private static String getInsertValue(Map<Integer, Column>  columns, Map<String, String>  columnValue, QueryOption queryOption) {
		StringBuffer sb = new StringBuffer();
		sb.append(" VALUES (");
		sb.append(getInsertValues(columns, columnValue, queryOption));
		sb.append(") ");
		return sb.toString();		
	}
	
	private static String getInsertValues (			 
			 Map<Integer, Column> columns,
			 Map<String, String>  columnValue,
			 QueryOption queryOption) {
		int size = columns.size();
		StringBuffer sb = new StringBuffer("");
		boolean isBeginning = true;
		boolean  timeAsFunction = queryOption.isTimeAsFunction();
		for (int i = 0; i < size; i++) {
			Column column = columns.get(Integer.valueOf(i));
			String columnName = column.getName(); //columns.get(Integer.valueOf(i));
			String value = columnValue.get(columnName);		
			if (value!=null && !value.equals("")) {
				if (isBeginning) {
					isBeginning=false;
				}else
					sb.append(",");
				formatColumnValue(sb, timeAsFunction, column, value);
			}
		}
		return sb.toString();		
	}

	private static void formatColumnValue(StringBuffer sb,
			boolean timeAsFunction, Column column, String value) {
		if (isQuoted(column, timeAsFunction)){
			sb.append("'"+value+"'");
		} else {
			sb.append(value);
		}
	}
	
	public static String buildQuery(
			 String table,
			 SelectOption selectOption,
			 Map<Integer, String> columnIndex,
			 Map<String, String>  columnExpressionValue,
			 Map<String, String>  columnValue,
			 Map<String, String>  columnOrderValue) {
		StringBuffer sb = new StringBuffer();
		sb.append(getWhatQuery(columnIndex, selectOption));
		sb.append(getQueryFrom(table));
		sb.append(getWhereQuery(columnIndex, columnExpressionValue, columnValue, new QueryOption()));
		sb.append(getQueryOrder(columnIndex, columnOrderValue));
		_logger.debug(" query = "+ sb.toString());
		System.out.println(" query = "+ sb.toString());
		return sb.toString();
	}
	
	public static String buildQuery(
			String table,
			Map<Integer, String> columnIndex,
			Map<String, String>  columnExpressionValue,
			Map<String, String>  columnValue,
			Map<String, String>  columnOrderValue) {
		return buildQuery(table, null, columnIndex, columnExpressionValue, columnValue, columnOrderValue);
	}

	private static String getWhatQuery (Map<Integer, String> columnIndex, SelectOption selectOption) {
		StringBuffer sb = new StringBuffer("SELECT ");
		sb.append(appendSelectOption(selectOption));
		sb.append(getColumnQuery(columnIndex));
		return sb.toString();
	}
	
	private static String appendSelectOption (SelectOption selectOption) {
		if (selectOption!=null)
			return " "+selectOption.value()+" ";
		return "";
	}
	
	private static String getColumnQuery (Map<Integer, String> columnIndex) {
		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("");
		for (int i = 1; i <= size; i++) {
			String s = columnIndex.get(Integer.valueOf(i));
			sb.append(s);
			if (i!=size)
				sb.append(", ");
		}
		return sb.toString();
	}
	
	private static String getUpdateSetQuery (
			Map<Integer, Column> columns,
			Map<Integer, String> columnIndex, 
			Map<String, String>  columnValue, 
			QueryOption queryOption) {
		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("");
		boolean isBeginning = true;
		boolean  timeAsFunction = queryOption.isTimeAsFunction();
		for (int i = 0; i <size; i++) {
			Column column = columns.get(Integer.valueOf(i));
			String columnName = columnIndex.get(Integer.valueOf(i));
			String value = columnValue.get(columnName);
			if (value!=null && !value.equals("")) {
				if (isBeginning) {
					isBeginning=false;
					sb.append(" SET "+columnName+" = ");
				}else
					sb.append(", "+columnName+" = ");
//				sb.append(" SET "+columnName+" = ");
				formatColumnValue(sb, timeAsFunction, column, value);
//				if (isQuoted(value)){
//					sb.append("'"+value+"'");
//				}
			}
		}
		return sb.toString();
	}
	
	private static String getWhereQueryCRUD (
			Map<Integer, Column> columns,
			Map<Integer, String> columnWhereIndex, 
			Map<String, String>  columnWhereValue,
			QueryOption queryOption) {
		int size = columnWhereIndex.size();
		StringBuffer sb = new StringBuffer("");
		boolean isBeginning = true;
		boolean  timeAsFunction = queryOption.isTimeAsFunction();
		for (int i = 0; i <size; i++) {
			Column column = columns.get(Integer.valueOf(i));
			String columnName = columnWhereIndex.get(Integer.valueOf(i));
			String value = columnWhereValue.get(columnName);
			if (value!=null && !value.equals("")) {				
				if (isBeginning) {
					isBeginning=false;
					sb.append(" WHERE ");
					
				}else
					sb.append(" AND ");
				sb.append(columnName+" = ");
				formatColumnValue(sb, timeAsFunction, column, value);
//				if (isQuoted(value)){
//					sb.append("'"+value+"'");
//				}
			}
		}
		return sb.toString();
	}

	private static String getColumnName(Map<Integer, Column> columns, int i) {
		Column column = columns.get(Integer.valueOf(i));
		return column.getName(); 
	}
	
	private static String getColumnQuery2 (Map<Integer, Column> columns, Map<String, String>  columnValue) {
		int size = columns.size();
		StringBuffer sb = new StringBuffer("");
		boolean isBeginning = true;
		for (int i = 0; i <size; i++) {
			String columnName = getColumnName(columns, i); 
			String value = columnValue.get(columnName);
			if (value!=null && !value.equals("")) {
				if (isBeginning) {
					isBeginning=false;
				}else
					sb.append(",");
				if (isQuoted(columnName)){
					sb.append(columnName);
				}
			}
		}
		return sb.toString();
	}	
	
	private static String getQueryFrom (String table) {
		return " FROM "+table+" ";
	}
	
	private static String getQueryOrder(Map<Integer, String> columnIndex, Map<String, String> columnOrderValue) {
		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("");
		boolean isCommaSet = false;
		for (int i = 0; i <= size; i++) {
			String columnName = columnIndex.get(Integer.valueOf(i));
			String order = columnOrderValue.get(columnName);
//			System.out.println(">>>>>>> i ="+i+" order = "+order + " columnName = "+columnName);
			if (order!=null && !order.equals("") && !order.equals("blank") && !order.equals("null")) {
				if (isCommaSet==false) {
					sb.append(" ORDER BY ");
					isCommaSet = true;
				} else {
					sb.append(",");
				}
				sb.append(columnName);
				sb.append(" "+order);
			}
		}
		return sb.toString();			
	}
	
	private static String getWhereQuery (			 
			 Map<Integer, String> columnIndex,
			 Map<String, String>  columnExpressionValue,
			 Map<String, String>  columnValue,
			 QueryOption queryOption) {
		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("");
		boolean isWhereSet = false;
		for (int i = 0; i < size+1; i++) {
			String columnName = columnIndex.get(Integer.valueOf(i));
			String expression = columnExpressionValue.get(columnName);
			String value = columnValue.get(columnName);
			if (expression!=null && !expression.equals("") && !expression.equals("blank") && !expression.equals("null")) {
				if (isWhereSet==false) {
					sb.append(" WHERE ");
					isWhereSet = true;
				} else {
					sb.append(" AND ");
				}
				sb.append(columnName);
				if ("startsWith".equals(expression)) {
					sb.append(" like ");
					sb.append("'"+value+"%'");
				} else if ("contains".equals(expression)) {
					sb.append(" like ");
					sb.append("'%"+value+"%'");
				} else if ("endsWith".equals(expression)) {
					sb.append(" like ");
					sb.append("'%"+value+"'");
				} else if ("in".equals(expression)) {
					sb.append(" in (");
					sb.append(ParserUtils.getQuotedElementListAsString(value));
					sb.append(")");
				} else {
					sb.append(expression);
					sb.append("'"+value+"'");
				}
			}
		}
		return sb.toString();		
	}
	
	public static boolean isQuoted (Column column, boolean timeAsFunction) {
		FieldType type = column.getType();
		if (timeAsFunction &&(FieldType.TIMESTAMP.equals(type) || FieldType.DATE.equals(type) || FieldType.TIME.equals(type)))
			return false;
		return true;
	}
	
	public static boolean isQuoted (String name) {
		return true;
	}

//	public static String buildQuery(String jdbcQuery,
//			Map<Integer, Object> inputIndex) {
//		return null;
//	}

	public static int getIntFromQueryQuery(String jdbcQuery) throws SQLException {
		return Integer.valueOf(executeQuery(jdbcQuery)[0][0].toString());
	}
	
	public static String getStringFromQueryQuery(String jdbcQuery) throws SQLException {
		return executeQuery(jdbcQuery)[0][0].toString();
	}
	
	public static void getVoidFromQueryQuery(String jdbcQuery) throws SQLException {
		executeQuery(jdbcQuery);
	}
	
	public static Object[][] executeQuery(String jdbcQuery) throws SQLException {
		Connection connection = DatabaseUtils.getConnection();
		if (connection ==null)
			System.out.println("connection is null");
		Statement ps = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery(jdbcQuery);
		Object[][] table = getResultSet(rs);
		connection.close();
		return table;
	}

	public static Object[][] getResultSet(ResultSet rs) throws SQLException {
		int len = rs.getMetaData().getColumnCount();
		
		List<Object[]> list = new ArrayList<Object[]>() ;
		while (rs.next())  {
			Object [] row = new Object[len];
			for (int j = 0; j < len; j++) {
				Object o = rs.getObject(j+1);
				if (o==null)
					o = new String (">null value returned<");
				row[j]=o;
			}
			list.add(row);
		}
		Object [][] table =  new Object[list.size()][];
		for (int i = 0; i< list.size(); i++) {
			table[i]=list.get(i);
		}
		return table;
	}
}
