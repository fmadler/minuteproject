package net.sf.minuteProject.plugin.vaadin;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ColumnDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.DatabaseDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VaadinPluginTest {

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
	public void testInteger() {
		when(column.getType()).thenReturn("INTEGER");
		when(column.getScale()).thenReturn(0);
		String getConverter = VaadinPlugin.getConverter(column);
		Assertions.assertThat("getInteger").isEqualTo(getConverter);
	}

	@Test
	public void testClob() {
		when(column.getType()).thenReturn("CLOB");
		when(column.getScale()).thenReturn(0);
		String getConverter = VaadinPlugin.getConverter(column);
		Assertions.assertThat("getString").isEqualTo(getConverter);
	}

	@Test
	public void testBlob() {
		when(column.getType()).thenReturn("BLOB");
		when(column.getScale()).thenReturn(0);
		String getConverter = VaadinPlugin.getConverter(column);
		Assertions.assertThat("getBytes").isEqualTo(getConverter);
	}
}
