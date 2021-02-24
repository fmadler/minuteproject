package net.sf.minuteproject.sample;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import net.sf.minuteproject.fitnesse.fixture.DbInsertUpdateDeleteFixture;
import net.sf.minuteproject.fitnesse.fixture.query.QueryOption;
import net.sf.minuteproject.model.db.Column;
import net.sf.minuteproject.model.db.type.FieldType;
import net.sf.minuteproject.utils.query.QueryUtils;

import org.junit.Test;

public class SampleTimeStampColumnFixtureTest extends DbInsertUpdateDeleteFixture{

	private static final String EXPECTED_UPDATE = "UPDATE TABLE_A  SET VC_PUSH_TO_ADOPTION_DATE = to_date( lpad('01/01/1800', 10, '0') , 'DD/MM/YYYY')  WHERE VC_IDNAME = 'CIS_CONSULT_A'";

   public String VC_IDNAME, WHERE_VC_IDNAME; //vcIdname; // 

   public String VC_PUSH_TO_ADOPTION_DATE, WHERE_VC_PUSH_TO_ADOPTION_DATE; //vcPushToAdoptionDate; // 


	public static int NB_COLUMN = 30;
	
	protected int getNumberOfColumn() {
		return NB_COLUMN;
	}
	 
	protected String getTable() {
		return "TABLE_A";
	}
	
	protected Map<Integer, String> getColumnIndex () {
		Map<Integer, String> columnIndex = new HashMap<Integer, String>();
		int i = 0;
	    columnIndex.put(i++, "VC_IDNAME"); 
	    columnIndex.put(i++, "VC_PUSH_TO_ADOPTION_DATE"); 
	    return columnIndex;
	}
	
	protected Map<String, String> getColumnValue () {
	   Map<String, String> columnValue = new HashMap<String, String>();
	   columnValue.put("VC_IDNAME", VC_IDNAME); 
	   columnValue.put("VC_PUSH_TO_ADOPTION_DATE", VC_PUSH_TO_ADOPTION_DATE); 
	   return columnValue;
	 }	
	
	protected Map<Integer, String> getColumnWhereIndex () {
	   Map<Integer, String> columnIndex = new HashMap<Integer, String>();
	   int i = 0;
	   columnIndex.put(i++, "VC_IDNAME"); 
	   columnIndex.put(i++, "VC_PUSH_TO_ADOPTION_DATE"); 
	   return columnIndex;
	 }
	
	protected Map<String, String> getColumnWhereValue () {
	   Map<String, String> columnValue = new HashMap<String, String>();
	   columnValue.put("VC_IDNAME", WHERE_VC_IDNAME); 
	   columnValue.put("VC_PUSH_TO_ADOPTION_DATE", WHERE_VC_PUSH_TO_ADOPTION_DATE); 
	   return columnValue;
	}		

	protected Map<Integer, Column> getColumns() {
	   Map<Integer, Column> columnIndex = new HashMap<Integer, Column>();
	   int i = 0;
	   columnIndex.put(i++, new Column("VC_IDNAME", FieldType.VARCHAR, false, 255)); //java.lang.String
	   columnIndex.put(i++, new Column("VC_PUSH_TO_ADOPTION_DATE", FieldType.TIMESTAMP, false, 0)); //java.sql.Timestamp
	   return columnIndex;
	}	
	
	@Test
	public void testUpdateStatement () {
		VC_PUSH_TO_ADOPTION_DATE = "to_date( lpad('01/01/1800', 10, '0') , 'DD/MM/YYYY')";
		WHERE_VC_IDNAME = "CIS_CONSULT_A";
		QueryOption queryOption = new QueryOption();
		queryOption.setTimeAsFunction(true);
		String query = QueryUtils.buildUpdateStatement(getTable(), getColumns(), getColumnIndex(), getColumnValue(), getColumnWhereIndex(), getColumnWhereValue(), queryOption);
		Assert.assertTrue (query, query.equals(EXPECTED_UPDATE));
	}
}
