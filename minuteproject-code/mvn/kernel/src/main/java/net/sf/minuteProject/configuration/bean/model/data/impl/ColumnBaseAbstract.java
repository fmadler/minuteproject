package net.sf.minuteProject.configuration.bean.model.data.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;
import lombok.Setter;
import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidation;
import net.sf.minuteProject.configuration.bean.model.data.BaseColumn;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParamLink;
@Getter
@Setter
public abstract class ColumnBaseAbstract extends AbstractConfiguration implements BaseColumn{

	private String typeAlias;
	private boolean isHidden=false, isTransient=false, isSearchable=true, isEditable=true;
	private boolean isContext=false, isImplicit=false, hasBeenDuplicated=false, isArray = false, isStructuredArray=false, isOutputParam=false;
	private String filterName, sessionParamName, separatorCharacters, arrayColumns, arrayColumnsType;
	private QueryParamLink queryParamLink;
	private boolean useJwtSubject = false;

	private List<FieldValidation> fieldValidations = new ArrayList<>();
	
	@Override
	public String getTypeAlias() {
		if (typeAlias==null)
			typeAlias=getType();
		return typeAlias;
	}
	
	public void setTypeAlias(String typeAlias){
		this.typeAlias = typeAlias;
	}
	
	@Override
	public boolean isHidden() {
		return isHidden;
	}
	@Override
	public void setHidden(Boolean isHidden) {
		if (isHidden!=null)
		this.isHidden = isHidden;
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}
	@Override
	public void setEditable(Boolean isEditable) {
		if (isEditable!=null)
			this.isEditable = isEditable;
	}
	@Override
	public boolean isSearchable() {
		return isSearchable;
	}
	@Override
	public void setSearchable(Boolean isSearchable) {
		if (isSearchable!=null)
			this.isSearchable = isSearchable;
	}
	@Override
	public boolean isTransient() {
		return isTransient;
	}
	@Override
	public void setTransient(Boolean isTransient) {
		if (isTransient!=null)
			this.isTransient = isTransient;
	}

	public boolean isContext() {
		return isContext;
	}

	public void setContext(boolean isContext) {
		this.isContext = isContext;
	}

	public boolean isImplicit() {
		return isImplicit || useJwtSubject || !StringUtils.isEmpty(sessionParamName) ;
	}

	public void setImplicit(boolean isImplicit) {
		this.isImplicit = isImplicit;
	}

	public boolean hasBeenDuplicated() {
		return hasBeenDuplicated;
	}

	public void setHasBeenDuplicated(boolean hasBeenDuplicated) {
		this.hasBeenDuplicated = hasBeenDuplicated;
	}
	
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
	public String getFilterName() {
		return filterName;
	}

	public boolean isFilter() {
		return filterName!=null;
	}
	
	public QueryParamLink getQueryParamLink () {
		return queryParamLink;
	}
	
	public void setQueryParamLink (QueryParamLink queryParamLink) {
		this.queryParamLink = queryParamLink;
	}
	
	public void setIsOutputParam (boolean isOutputParam) {
		this.isOutputParam = isOutputParam;
	}
	
	public boolean isOutputParam() {
		return isOutputParam;
	}
	
	
	public boolean isArray () {
		return isArray;
	}
	
	public void setIsArray(boolean isArray) {
		this.isArray = isArray;
	}

	public String getSessionParamName() {
		return sessionParamName;
	}

	public void setSessionParamName(String sessionParamName) {
		this.sessionParamName = sessionParamName;
	}

	public void setUseJwtSubject(boolean useJwtSubject) {
		this.useJwtSubject = useJwtSubject;
	}

	public boolean useJwtSubject() {
		return this.useJwtSubject;
	}
	
	public void setValidations(List<FieldValidation> fieldValidations) {
		this.fieldValidations = fieldValidations;
	}
	
	public List<FieldValidation> getValidations() {
		return fieldValidations;
	}
	
	public List<Field> getStructuredArray() {
		return Arrays.asList(getArrayColumns().split(",")).stream()
			.map(u -> {
				Field f = new Field();
				f.setName(u);
				f.setType("string");
				return f;
			})
			.collect(Collectors.toList());
	}
	
	public boolean isStructuredArray () {
		return isStructuredArray;
	}
	
	public void setIsStructuredArray(boolean isStructuredArray) {
		this.isStructuredArray = isStructuredArray;
	}
	
	public void setSeparatorCharacters(String separatorCharacters) {
		this.separatorCharacters = separatorCharacters;
	}
	
	public String getSeparatorCharacters() {
		return this.separatorCharacters;
	}

	public void setArrayColumns(String arrayColumns) {
		this.arrayColumns = arrayColumns;	
	}

	public void setArrayColumnsType(String arrayColumnsType) {
		this.arrayColumnsType = arrayColumnsType;
	}
}
