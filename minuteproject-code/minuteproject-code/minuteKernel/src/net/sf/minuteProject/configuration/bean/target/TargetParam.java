package net.sf.minuteProject.configuration.bean.target;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class TargetParam extends AbstractConfiguration{

	private String value, defaultValue, required;

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
