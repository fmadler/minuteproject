package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.property.PropertyUtils;

public class GeneratorQualifier extends AbstractConfiguration {

	protected String version, packageRoot;
	protected Configuration configuration;

	public String getAlias() {
		return FormatUtils.getJavaName(super.getAlias()).toLowerCase();
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = PropertyUtils.convertValueIfSystemOrEnvironmentVariable(version);
	}

	public String getPackageRoot() {
		return packageRoot;
	}

	public void setPackageRoot(String packageRoot) {
		this.packageRoot = packageRoot;
	}
}
