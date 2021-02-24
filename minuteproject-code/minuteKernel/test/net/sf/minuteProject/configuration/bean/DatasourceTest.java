package net.sf.minuteProject.configuration.bean;

import junit.framework.Assert;
import static junit.framework.Assert.*;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class DatasourceTest {

	public static final String sampleMysqlDriverUrl = "jdbc:mysql://${server-name}:${port-number}/${database-name}";
	public static final String sampleDerbyDriverUrl = "jdbc:derby://${server-name}:${port-number}/${database-name}";
	public static final String sampleOracleDriverUrl = "jdbc:oracle:thin:@${server-name}:${port-number}:${SID}";
	public static final String sampleDB2DriverUrl = "jdbc:db2://${server-name}:${port-number}/${database-name}";
	public static final String samplePostgresDriverUrl = "jdbc:postgresql://${server-name}:${port-number}:${database-name}";
	public static final String sampleSqlserverDriverUrl = "jdbc:sqlserver://${server-name}:${port-number};databaseName=${database-name}";
	
	public static final String serverNameKey = "${server-name}";
	public static final String serverPortKey = "${port-number}";
	public static final String databaseInstanceKey = "${database-name}";
	public static final String databaseSIDKey = "${SID}";
	
	BasicDataSource bds;
	@Before 
	public void init() {
		bds = mock(BasicDataSource.class);		
	}
	
	@Test
	public void testMysql () {
		when(bds.getUrl()).thenReturn(sampleMysqlDriverUrl);
		Datasource datasource = new Datasource(bds, "mysql");
		assertDataSourceParams(datasource);
	}

	private void assertDataSourceParams(Datasource datasource) {
		assertEquals(datasource.getServer(), serverNameKey);
		assertEquals(datasource.getPort(), serverPortKey);
		assertEquals(datasource.getDatabaseInstance(), databaseInstanceKey);
	}
	
	@Test
	public void testDerby () {
		when(bds.getUrl()).thenReturn(sampleDerbyDriverUrl);
		Datasource datasource = new Datasource(bds, "derby");
		assertDataSourceParams(datasource);
	}
	
	@Test
	public void testDB2 () {
		when(bds.getUrl()).thenReturn(sampleDB2DriverUrl);
		Datasource datasource = new Datasource(bds, "db2");
		assertDataSourceParams(datasource);
	}
	
	@Test
	public void testPostgres () {
		when(bds.getUrl()).thenReturn(samplePostgresDriverUrl);
		Datasource datasource = new Datasource(bds, "postgres");
		assertDataSourceParams(datasource);
	}
	
	@Test
	public void testSqlserver() {
		when(bds.getUrl()).thenReturn(sampleSqlserverDriverUrl);
		Datasource datasource = new Datasource(bds, "sqlserver");
		assertDataSourceParams(datasource);
	}

	@Test
	public void testOracle () {
		when(bds.getUrl()).thenReturn(sampleOracleDriverUrl);
		Datasource datasource = new Datasource(bds, "oracle");
		assertEquals(datasource.getServer(), serverNameKey);
		assertEquals(datasource.getPort(), serverPortKey);
		assertEquals(datasource.getDatabaseInstance(), databaseSIDKey);
	}
	
	
}
