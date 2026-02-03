package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.utils.StringUtils;

public class Condition extends AbstractConfiguration {
	
	public static final String REGEX = "regex";
	public static final String ENDS_WITH = "endsWith";
	public static final String ENDS__WITH = "end-with";
	public static final String ENDS___WITH = "ends-with";
	public static final String CONTAINS = "contains";
	public static final String STARTS_WITH = "startsWith";
	public static final String STARTS__WITH = "start-with";
	public static final String STARTS___WITH = "starts-with";
	public static final String EQUALS = "equals";
	public static final String INCLUDE = "include";
	public static final String PACKAGE = "package";
	public static final String EXCLUDE = "exclude";
	private String databaseObjectType;
	private String type;
	private String startsWith;
	private String endsWith, equals;
	private String regex;
	private String result;


	public boolean isConditionFalse(GeneratorBean bean) {
		return !isConditionTrue(bean);
	}
	
	public boolean isConditionTrue(GeneratorBean bean) {
		//rem: combi on name and package not yet done
		return isConditionOnName(bean) || isConditionOnType(bean);
	}
	public boolean isConditionOnName(GeneratorBean bean) {
		String valueToTest = bean.getName();
		if (StringUtils.startsWithIgnoreCase(valueToTest, startsWith)==true) return true;
		if (StringUtils.equalsIgnoreCase(valueToTest, equals)==true) return true;
		if (StringUtils.endsWithIgnoreCase(valueToTest, endsWith)==true) return true;
		if (StringUtils.regex(valueToTest, regex)==true) return true;
		return false;
	}
	public boolean isConditionOnType(GeneratorBean bean) {
		if (databaseObjectType!=null) {
			if (bean instanceof net.sf.minuteProject.configuration.bean.model.data.Table) {
				String type = ((net.sf.minuteProject.configuration.bean.model.data.Table) bean).getType();
				return StringUtils.equalsIgnoreCase(databaseObjectType, type);
			}
			return false;
		}
		// by default do not care about the type
		return false;
	}

	public String getConditionResult(GeneratorBean bean) {
		if (isConditionTrue(bean))
			return result;
		return null;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getStartsWith() {
		return startsWith;
	}
	public void setStartsWith(String startsWith) {
		this.startsWith = startsWith;
	}
	public String getEndsWith() {
		return endsWith;
	}
	public void setEndsWith(String endsWith) {
		this.endsWith = endsWith;
	}
	
	public String getEquals() {
		return equals;
	}

	public void setEquals(String equals) {
		this.equals = equals;
	}

	public String getType() {
		return type;
	}
	public String getDatabaseObjectType() {
		return databaseObjectType;
	}

	public void setDatabaseObjectType(String databaseObjectType) {
		this.databaseObjectType = databaseObjectType;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
