package net.sf.minuteProject.plugin.vaadin;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ColumnDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.DatabaseDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VaadinPluginTest {

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
	public void testInteger() {
		when(column.getType()).thenReturn("NUMERIC");
		when(column.getScale()).thenReturn(0);
		String getConverter = VaadinPlugin.getConverter(column);
		Assert.assertTrue("should be getInteger ; but get:  "+getConverter,"getInteger".equals(getConverter));
	}
	@Test
	public void testClob() {
		when(column.getType()).thenReturn("CLOB");
		when(column.getScale()).thenReturn(0);
		String getConverter = VaadinPlugin.getConverter(column);
		Assert.assertTrue("should be getString ; but get:  "+getConverter,"getString".equals(getConverter));
	}
	@Test
	public void testBlob() {
		when(column.getType()).thenReturn("BLOB");
		when(column.getScale()).thenReturn(0);
		String getConverter = VaadinPlugin.getConverter(column);
		Assert.assertTrue("should be getBytes ; but get:  "+getConverter,"getBytes".equals(getConverter));
	}
}
