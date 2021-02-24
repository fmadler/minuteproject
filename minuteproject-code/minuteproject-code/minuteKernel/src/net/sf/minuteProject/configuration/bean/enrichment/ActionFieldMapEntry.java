package net.sf.minuteProject.configuration.bean.enrichment;

import org.apache.commons.lang.StringUtils;

public class ActionFieldMapEntry {

	private String outputField;
	private String requestedField;
	private String implicitField;
	private String inputField;
	private String staticValue;
	private String filterField;
	private String columnField;
	private String toActionField;
	private String convertCase;
	private String computedCase;

	public String getInputField() {
		return inputField;
	}

	public void setInputField(String inputField) {
		this.inputField = inputField;
	}

	public String getComputedCase() {
		return computedCase;
	}

	public void setComputedCase(String computedCase) {
		this.computedCase = computedCase;
	}

	public String getOutputField() {
		return outputField;
	}

	public void setOutputField(String outputField) {
		this.outputField = outputField;
	}

	public String getToActionField() {
		return toActionField;
	}

	public void setToActionField(String toActionField) {
		this.toActionField = toActionField;
	}

	public String getFilterField() {
		return filterField;
	}

	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}

	public boolean isOutputFieldMapping () {
		return !StringUtils.isEmpty(outputField);
	}
	
	public boolean isRequestedFieldMapping () {
		return !StringUtils.isEmpty(requestedField);
	}
	
	public String getConvertCase() {
		return convertCase;
	}

	public void setConvertCase(String convertCase) {
		this.convertCase = convertCase;
	}

	public boolean isFilterFieldMapping () {
		return !isOutputFieldMapping () && !StringUtils.isEmpty(filterField);
	}
	
	public boolean isColumnFieldMapping () {
		return !isFilterFieldMapping () && !StringUtils.isEmpty(columnField);
	}
	
	public boolean isUpperCaseConvert () {
		return "uppercase".equals(convertCase);
	}

	public String getColumnField() {
		return columnField;
	}

	public void setColumnField(String columnField) {
		this.columnField = columnField;
	}

	public String getStaticValue() {
		return staticValue;
	}

	public void setStaticValue(String staticValue) {
		this.staticValue = staticValue;
	}
	

	public String getRequestedField() {
		return requestedField;
	}

	public void setRequestedField(String requestedField) {
		this.requestedField = requestedField;
	}

	public String getImplicitField() {
		return implicitField;
	}

	public void setImplicitField(String implicitField) {
		this.implicitField = implicitField;
	}

	public String getOrigin() {
		if (!StringUtils.isEmpty(staticValue)) {
			return "static-value";
		}
		if (!StringUtils.isEmpty(inputField)) {
			return "input-field";
		}
		return "output-field";
	}

	@Override
	public String toString() {
		return "ActionFieldMapEntry [outputField=" + outputField + ", requestedField=" + requestedField
				+ ", implicitField=" + implicitField + ", inputField=" + inputField + ", staticValue=" + staticValue
				+ ", filterField=" + filterField + ", columnField=" + columnField + ", toActionField=" + toActionField
				+ ", convertCase=" + convertCase + ", computedCase=" + computedCase + "]";
	}
	
	
}
