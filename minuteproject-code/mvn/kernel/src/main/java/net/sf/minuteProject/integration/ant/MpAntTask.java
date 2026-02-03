package net.sf.minuteProject.integration.ant;

import net.sf.minuteProject.application.ModelViewGenerator;
import net.sf.minuteProject.configuration.bean.strategy.datamodel.PrimaryKeyPolicyPatternEnum;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class MpAntTask extends Task {

	private ModelViewGenerator modelViewGenerator;

	private String schema, driver, url, username, password, primaryKeyPolicy,
			target, database, rootpackage, businesspackage, version,
			configuration;

	public void execute() {
		log("MinuteProject generator", Project.MSG_INFO);
		modelViewGenerator = (configuration != null) ? new ModelViewGenerator(
				configuration) : new ModelViewGenerator(getBic());
		try {
			modelViewGenerator.generate();
		} catch (MinuteProjectException e) {
			log("MinuteProject generator error " + e.getMessage(),
					Project.MSG_ERR);
		}
	}

	private BasicIntegrationConfiguration getBic() {
		BasicIntegrationConfiguration bic = new BasicIntegrationConfiguration();
		bic.setSchema(schema);
		bic.setDriver(driver);
		bic.setUrl(url);
		bic.setUsername(username);
		bic.setPassword(password);
		bic.setPrimaryKeyPolicy(PrimaryKeyPolicyPatternEnum.getPrimaryKeyPolicy(primaryKeyPolicy));
		bic.setTargetTechnology(target);
		bic.setDatabase(database);
		bic.setRootpackage(rootpackage);
		bic.setBusinesspackage(businesspackage);
		bic.setVersion(version);
		return bic;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrimaryKeyPolicy() {
		return primaryKeyPolicy;
	}

	public void setPrimaryKeyPolicy(String primaryKeyPolicy) {
		this.primaryKeyPolicy = primaryKeyPolicy;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getRootpackage() {
		return rootpackage;
	}

	public void setRootpackage(String rootpackage) {
		this.rootpackage = rootpackage;
	}

	public String getBusinesspackage() {
		return businesspackage;
	}

	public void setBusinesspackage(String businesspackage) {
		this.businesspackage = businesspackage;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
