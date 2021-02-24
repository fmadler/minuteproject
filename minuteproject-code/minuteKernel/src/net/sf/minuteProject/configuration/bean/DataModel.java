package net.sf.minuteProject.configuration.bean;

import javax.sql.DataSource;

//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.connection.Driver;
import net.sf.minuteProject.configuration.bean.model.data.DataModelFactory;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.strategy.datamodel.PrimaryKeyPolicy;
import net.sf.minuteProject.utils.property.PropertyUtils;

public class DataModel {
	
	private Model model;
	private Database database;
	private DataSource datasource;
	private Driver driver;
	private FileSource fileSource;
	private String schema;
	private String databaseType;
	private PrimaryKeyPolicy primaryKeyPolicy;
	private boolean isSystemOrEnvironmentConverted = false;
	
	private BasicDataSource basicDataSource;
	
	public BasicDataSource getBasicDataSource() {
		if (!isSystemOrEnvironmentConverted) {
			isSystemOrEnvironmentConverted=true;
			basicDataSource.setUrl(PropertyUtils.convertValueIfSystemOrEnvironmentVariable(basicDataSource.getUrl()));
			basicDataSource.setUsername(PropertyUtils.convertValueIfSystemOrEnvironmentVariable(basicDataSource.getUsername()));
			basicDataSource.setPassword(PropertyUtils.convertValueIfSystemOrEnvironmentVariable(basicDataSource.getPassword()));
		}
		return basicDataSource;
	}

	public void setBasicDataSource(BasicDataSource basicDataSource) {
		this.basicDataSource = basicDataSource;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	/**
	 * real load of the database structure
	 */
	public void loadDatabase () { 
		database = DataModelFactory.getInstance().getDatabase(this);
	}	

	public Database getDatabase() {
		return database;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public FileSource getFileSource() {
		return fileSource;
	}

	public void setFileSource(FileSource filesource) {
		this.fileSource = filesource;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = StringUtils.upperCase(schema);
	}

	public PrimaryKeyPolicy getPrimaryKeyPolicy() {
		return primaryKeyPolicy;
	}

	public void setPrimaryKeyPolicy(PrimaryKeyPolicy primaryKeyPolicy) {
		this.primaryKeyPolicy = primaryKeyPolicy;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	public boolean hasSchema () {
		return (getSchema()!=null && !"".equals(getSchema()))?true:false;
	}
	
}
