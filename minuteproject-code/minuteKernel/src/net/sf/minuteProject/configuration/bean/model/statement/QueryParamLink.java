package net.sf.minuteProject.configuration.bean.model.statement;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.Field;

public class QueryParamLink extends AbstractConfiguration{
	
	private String entityName, queryName, fieldName, fieldKey, displayMode, autoCompleteFilter, autoCompleteMinChar, autoCompleteDelay;
	private String semanticReferenceFields;
	private QueryParam queryParam;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public QueryParam getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(QueryParam queryParam) {
		this.queryParam = queryParam;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}

	public String getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}

	public String getSemanticReferenceFields() {
		return semanticReferenceFields;
	}

	public void setSemanticReferenceFields(String semanticReferenceFields) {
		this.semanticReferenceFields = semanticReferenceFields;
	}

	public String getAutoCompleteFilter() {
		return autoCompleteFilter;
	}

	public void setAutoCompleteFilter(String autoCompleteFilter) {
		this.autoCompleteFilter = autoCompleteFilter;
	}

	public String getAutoCompleteMinChar() {
		return autoCompleteMinChar;
	}

	public void setAutoCompleteMinChar(String autoCompleteMinChar) {
		this.autoCompleteMinChar = autoCompleteMinChar;
	}

	public String getAutoCompleteDelay() {
		return autoCompleteDelay;
	}

	public void setAutoCompleteDelay(String autoCompleteDelay) {
		this.autoCompleteDelay = autoCompleteDelay;
	}
 
}
