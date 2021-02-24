/**
	* Copyright (c) minuteproject, minuteproject@gmail.com
	* All rights reserved.
	* 
	* Licensed under the Apache License, Version 2.0 (the "License")
	* you may not use this file except in compliance with the License.
	* You may obtain a copy of the License at
	* 
	* http://www.apache.org/licenses/LICENSE-2.0
	* 
	* Unless required by applicable law or agreed to in writing, software
	* distributed under the License is distributed on an "AS IS" BASIS,
	* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	* See the License for the specific language governing permissions and
	* limitations under the License.
	* 
	* More information on minuteproject:
	* twitter @minuteproject
	* wiki http://minuteproject.wikispaces.com 
	* blog http://minuteproject.blogspot.net
	* 
*/
package net.sf.minuteproject.sample;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import net.sf.minuteproject.fitnesse.fixture.DbInsertUpdateDeleteFixture;
import net.sf.minuteproject.fitnesse.fixture.query.QueryOption;
import net.sf.minuteproject.model.db.Column;
import net.sf.minuteproject.model.db.type.FieldType;
import net.sf.minuteproject.utils.query.QueryUtils;
import fit.ColumnFixture;

public class SampleColumnFixtureTest extends DbInsertUpdateDeleteFixture{

   private static final String UPDATE_T_SAMPLE_SET_ID_3_ACTIVE_0_WHERE_ID_3 = "UPDATE T_SAMPLE  SET ID = '3', ACTIVE = '0'  WHERE ID = '3'";
   public String ID, WHERE_ID; //id; // 
   public String ACTIVE, WHERE_ACTIVE; //active; // 
   public String COMMENTS, WHERE_COMMENTS; //comments; // 
   public String CREATION_DATE, WHERE_CREATION_DATE; //creationDate; // 
   public String CREATION_USER, WHERE_CREATION_USER; //creationUser; // 
   public String MODIFICATION_DATE, WHERE_MODIFICATION_DATE; //modificationDate; // 
   public String MODIFICATION_USER, WHERE_MODIFICATION_USER; //modificationUser; // 

	public static int NB_COLUMN = 7;
	
	protected int getNumberOfColumn() {
		return NB_COLUMN;
	}
	 
	protected String getTable() {
		return "T_SAMPLE";
	}
	
	protected Map<Integer, String> getColumnIndex () {
		Map<Integer, String> columnIndex = new HashMap<Integer, String>();
		int i = 0;
	    columnIndex.put(i++, "ID"); 
	    columnIndex.put(i++, "ACTIVE"); 
	    columnIndex.put(i++, "COMMENTS"); 
	    columnIndex.put(i++, "CREATION_DATE"); 
	    columnIndex.put(i++, "CREATION_USER"); 
	    columnIndex.put(i++, "MODIFICATION_DATE"); 
	    columnIndex.put(i++, "MODIFICATION_USER"); 
	    return columnIndex;
	}
	
	protected Map<String, String> getColumnValue () {
	   Map<String, String> columnValue = new HashMap<String, String>();
	   columnValue.put("ID", ID); 
	   columnValue.put("ACTIVE", ACTIVE); 
	   columnValue.put("COMMENTS", COMMENTS); 
	   columnValue.put("CREATION_DATE", CREATION_DATE); 
	   columnValue.put("CREATION_USER", CREATION_USER); 
	   columnValue.put("MODIFICATION_DATE", MODIFICATION_DATE); 
	   columnValue.put("MODIFICATION_USER", MODIFICATION_USER); 
	   return columnValue;
	 }	
	
	protected Map<Integer, String> getColumnWhereIndex () {
	   Map<Integer, String> columnIndex = new HashMap<Integer, String>();
	   int i = 0;
	   columnIndex.put(i++, "ID"); 
	   columnIndex.put(i++, "ACTIVE"); 
	   columnIndex.put(i++, "COMMENTS"); 
	   columnIndex.put(i++, "CREATION_DATE"); 
	   columnIndex.put(i++, "CREATION_USER"); 
	   columnIndex.put(i++, "MODIFICATION_DATE"); 
	   columnIndex.put(i++, "MODIFICATION_USER"); 
	   return columnIndex;
	 }
	
	protected Map<String, String> getColumnWhereValue () {
	   Map<String, String> columnValue = new HashMap<String, String>();
	   columnValue.put("ID", WHERE_ID); 
	   columnValue.put("ACTIVE", WHERE_ACTIVE); 
	   columnValue.put("COMMENTS", WHERE_COMMENTS); 
	   columnValue.put("CREATION_DATE", WHERE_CREATION_DATE); 
	   columnValue.put("CREATION_USER", WHERE_CREATION_USER); 
	   columnValue.put("MODIFICATION_DATE", WHERE_MODIFICATION_DATE); 
	   columnValue.put("MODIFICATION_USER", WHERE_MODIFICATION_USER); 
	   return columnValue;
	}		

	protected Map<Integer, Column> getColumns() {
	   Map<Integer, Column> columnIndex = new HashMap<Integer, Column>();
	   int i = 0;
	   columnIndex.put(i++, new Column("ID", FieldType.DECIMAL, true, 0)); //java.lang.Long
	   columnIndex.put(i++, new Column("ACTIVE", FieldType.BIT, false, 0)); //java.lang.Long
	   columnIndex.put(i++, new Column("COMMENTS", FieldType.VARCHAR, false, 400)); //java.lang.String
	   columnIndex.put(i++, new Column("CREATION_DATE", FieldType.TIMESTAMP, true, 0)); //java.sql.Timestamp
	   columnIndex.put(i++, new Column("CREATION_USER", FieldType.VARCHAR, true, 200)); //java.lang.String
	   columnIndex.put(i++, new Column("MODIFICATION_DATE", FieldType.TIMESTAMP, true, 0)); //java.sql.Timestamp
	   columnIndex.put(i++, new Column("MODIFICATION_USER", FieldType.VARCHAR, true, 200)); //java.lang.String
	   return columnIndex;
	}	

	@Test
	public void testUpdateStatement () {
		ID = "3";
		ACTIVE = "0";
		WHERE_ID = "3";
		String query = QueryUtils.buildUpdateStatement(getTable(), getColumns(), getColumnIndex(), getColumnValue(), getColumnWhereIndex(), getColumnWhereValue(), new QueryOption());
		Assert.assertTrue (query, query.equals(UPDATE_T_SAMPLE_SET_ID_3_ACTIVE_0_WHERE_ID_3));
	}
}
