package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ColumnDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.DatabaseDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;
import net.sf.minuteProject.model.db.type.FieldType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ConvertUtilsTest {

	Database database;
	Table table;
	Column column;
	@BeforeEach
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
		Assertions.assertThat(s).isEqualTo("String");
	}
	
	@Test
	public void testColumnOracleBigInt() {
		when(column.getType()).thenReturn("NUMBER");
		when(column.getSize()).thenReturn("19");
		
		String s = ConvertUtils.getJavaTypeClassFromDBType(column);
		Assertions.assertThat(s).isEqualTo("java.lang.Long");
	}
	
	@Test
	public void testColumnSmallInt() {
		when(column.getType()).thenReturn("SMALLINT");
//		when(column.getSize()).thenReturn("19");
		
		String s = ConvertUtils.getJavaTypeClassFromDBType(column);
		Assertions.assertThat(s).isEqualTo("Short");
		
		String s2 = ConvertUtils.getJavaTypeFromDBType("SMALLINT");
		Assertions.assertThat(s2).isEqualTo("Short");
	}
	
	@Test
	public void testColumnBit() {
		String s = ConvertUtils.getUMLTypeFromDBFullType("BIT");
		Assertions.assertThat(s).isEqualTo(ConvertUtils.UML_BOOLEAN_TYPE);
	}
	
	@Test
	public void testGetJavaTypeMask() {
		when(column.getType()).thenReturn("NUMBER");
		when(column.getTypeAlias()).thenReturn("NUMBER");
		when(column.getSize()).thenReturn("19");
		String s = ConvertUtils.getJavaTypeMask(column, "rowKey", true);
		Assertions.assertThat(s).isEqualTo("Long.valueOf(rowKey)");

	}
	
	@Test
	public void testGetJavaTypeMaskLong() {
		when(column.getType()).thenReturn("INTEGER");
		when(column.getTypeAlias()).thenReturn("INTEGER");
		String s = ConvertUtils.getJavaTypeMask(column, "1", true);
		Assertions.assertThat(s).isEqualTo("Integer.valueOf(1)");
	}
	
	@Test
	public void testGetJavaTypeMaskShort() {
		when(column.getType()).thenReturn("SMALLINT");
		when(column.getTypeAlias()).thenReturn("SMALLINT");
		String s = ConvertUtils.getJavaTypeMask(column, "rowKey", true);
		Assertions.assertThat(s).isEqualTo("Short.valueOf(rowKey)");

		s = ConvertUtils.getJavaTypeMask(column, "1", true);
		Assertions.assertThat(s).isEqualTo("Short.valueOf(1)");
		
		s = ConvertUtils.getJavaTypeMaskFormated(column, "1", true);
		Assertions.assertThat(s).isEqualTo("Short.valueOf(\"1\")");
	}
	
}
