package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ColumnDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.DatabaseDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;
import net.sf.minuteproject.model.db.type.FieldType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ConvertUtilsTest {

	Database database;
	Table table;
	Column column;
	@Before
	public void init(){
		database = mock(DatabaseDDLUtils.class);
		when(database.getType()).thenReturn("ORACLE");
		table = mock(TableDDLUtils.class);
		table.setDatabase(database);
		column = mock(ColumnDDLUtils.class);
		when(column.getTable()).thenReturn(table);
		
	}
	@Test
	public void testColumnOracleVarchar() {
		when(column.getType()).thenReturn(FieldType.VARCHAR.toString());
		String s = ConvertUtils.getJavaTypeClassFromDBType(column);
		Assert.assertTrue("s = "+s,s.equals("String"));
	}
	
	@Test
	public void testColumnOracleBigInt() {
		when(column.getType()).thenReturn("NUMBER");
		when(column.getSize()).thenReturn("19");
		
		String s = ConvertUtils.getJavaTypeClassFromDBType(column);
		Assert.assertTrue("s = "+s,s.equals("java.lang.Long"));
	}
	
	@Test
	public void testColumnSmallInt() {
		when(column.getType()).thenReturn("SMALLINT");
//		when(column.getSize()).thenReturn("19");
		
		String s = ConvertUtils.getJavaTypeClassFromDBType(column);
		Assert.assertTrue("s = "+s,s.equals("Short"));
		
		String s2 = ConvertUtils.getJavaTypeFromDBType("SMALLINT");
		Assert.assertTrue("s = "+s2,s2.equals("Short"));
	}
	
	@Test
	public void testColumnBit() {
		String s = ConvertUtils.getUMLTypeFromDBFullType("BIT");
		Assert.assertTrue("s = "+s,s.equals(ConvertUtils.UML_BOOLEAN_TYPE));
	}
	
	@Test
	public void testGetJavaTypeMask() {
		when(column.getType()).thenReturn("NUMBER");
		when(column.getTypeAlias()).thenReturn("NUMBER");
		when(column.getSize()).thenReturn("19");
		String s = ConvertUtils.getJavaTypeMask(column, "rowKey", true);
		Assert.assertTrue("s = "+s,s.equals("Long.valueOf(rowKey)"));
//		when(column.getType()).thenReturn("NUMBER");
//		when(column.getSize()).thenReturn("5");
//		s = ConvertUtils.getJavaTypeMask(column, "rowKey", true);
//		Assert.assertTrue("s = "+s,s.equals("Integer.valueOf(rowKey)"));
		
		
		
//		column.setType("NUMBER");
//		column.setTypeAlias("NUMBER");
//		column.setSize("19");
	}
	
	@Test
	public void testGetJavaTypeMaskLong() {
		when(column.getType()).thenReturn("INTEGER");
		when(column.getTypeAlias()).thenReturn("INTEGER");
		String s = ConvertUtils.getJavaTypeMask(column, "rowKey", true);
		Assert.assertTrue("s = "+s,s.equals("Integer.valueOf(rowKey)"));
	}
	
	@Test
	public void testGetJavaTypeMaskShort() {
		when(column.getType()).thenReturn("SMALLINT");
		when(column.getTypeAlias()).thenReturn("SMALLINT");
		String s = ConvertUtils.getJavaTypeMask(column, "rowKey", true);
		Assert.assertTrue("s = "+s,s.equals("Short.valueOf(rowKey)"));

		s = ConvertUtils.getJavaTypeMask(column, "1", true);
		Assert.assertTrue("s = "+s,s.equals("Short.valueOf(1)"));
		
		s = ConvertUtils.getJavaTypeMaskFormated(column, "1", true);
		Assert.assertTrue("s = "+s,s.equals("Short.valueOf(\"1\")"));
	}
	
}
