package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidation;
import net.sf.minuteProject.configuration.bean.enumeration.Scope;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.TableUtils;

public class QueryParam extends Field {

	private QueryParams queryParams;
	private boolean isImplicit  =false;
	private boolean isContext   =false;
	private boolean hasBeenDuplicated   =false;
	private String sample;
	private String sessionParamName;
	private boolean useJwtSubject=false;
	private String defaultValueWhenNull;
	private String refname;
	private int size, scale;
	private String converter;
	private String likeMode;
	private QueryParamLink queryParamLink;
	private boolean isFilter=false, isArray=false, isOutputParam = false;
	private String regexStart, regexEnd, regexParamWrapper, regexParamSeparator;
	private Integer index;
	private List<FieldValidation> fieldValidations = new ArrayList<>();
	private Scope scope = Scope.ALL_STACKS;
	private boolean omitFromSddLookupQuery = false;
	
	@Override
	public String getSizeOrDefault() {
		return String.valueOf(getSize());
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getSample() {
		return sample;
	}
	public void setSample(String sample) {
		this.sample = sample;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getConverter() {
		return converter;
	}
	public void setConverter(String converter) {
		this.converter = converter;
	}
	public QueryParamLink getQueryParamLink() {
		return queryParamLink;
	}
	public void setQueryParamLink(QueryParamLink queryParamLink) {
		this.queryParamLink = queryParamLink;
		this.queryParamLink.setQueryParam(this);
	}

	public Field getLinkField() {
		if (queryParamLink!=null && queryParamLink.getEntityName()!=null) {
			setLinkToTargetEntity(queryParamLink.getEntityName());
			Column column = ColumnUtils.getColumn(TableUtils.getTable(getQueryParams().getQuery().getQueries().getStatementModel().getModel().getDataModel().getDatabase(), queryParamLink.getEntityName()), queryParamLink.getFieldName());
			if (column!=null) {
				setType(column.getType());
				setDefaultValue(column.getDefaultValue());
				setSize(column.getSizeAsInt());
				Column primaryFirstColumn = TableUtils.getPrimaryFirstColumn(column.getTable());
				if (primaryFirstColumn!=null) {
					String pk = primaryFirstColumn.getName();
					setLinkToTargetField(pk);	
				}
			}
			
		}
		return this;
	}
	
	public boolean isLink() {
		return (queryParamLink!=null)?true:false;
	}

	public QueryParams getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
	}
	public String getLikeMode() {
		return likeMode;
	}
	public void setLikeMode(String likeMode) {
		this.likeMode = likeMode;
	}
	public boolean isImplicit() {
		return isImplicit;
	}
	public void setIsImplicit(boolean isImplicit) {
		this.isImplicit = isImplicit;
	}
	public boolean isContext() {
		return isContext;
	}
	public void setIsContext(boolean isContext) {
		this.isContext = isContext;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String getRefname() {
		return refname;
	}
	@Override
	public void setRefname(String refname) {
		this.refname = refname;
	}
	public boolean hasBeenDuplicated() {
		return hasBeenDuplicated;
	}
	public void setHasBeenDuplicated(boolean hasBeenDuplicated) {
		this.hasBeenDuplicated = hasBeenDuplicated;
	}
	public boolean isFilter() {
		return queryParams.isFilter();
	}
	public String getFilterName() {
		return null;
	}
	
	public boolean isArray () {
		return isArray;
	}
	
	public void setIsArray(boolean isArray) {
		this.isArray = isArray;
	}
	public boolean isOutputParam() {
		return isOutputParam;
	}
	public void setIsOutputParam(boolean isOutputParam) {
		this.isOutputParam = isOutputParam;
	}
	public String getRegexStart() {
		return regexStart;
	}
	public void setRegexStart(String regexStart) {
		this.regexStart = regexStart;
	}
	public String getRegexEnd() {
		return regexEnd;
	}
	public void setRegexEnd(String regexEnd) {
		this.regexEnd = regexEnd;
	}
	public String getRegexParamWrapper() {
		return regexParamWrapper;
	}
	public void setRegexParamWrapper(String regexParamWrapper) {
		this.regexParamWrapper = regexParamWrapper;
	}
	public String getRegexParamSeparator() {
		return regexParamSeparator;
	}
	public void setRegexParamSeparator(String regexParamSeparator) {
		this.regexParamSeparator = regexParamSeparator;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getSessionParamName() {
		return sessionParamName;
	}
	public void setSessionParamName(String sessionParamName) {
		this.sessionParamName = sessionParamName;
	}

	public boolean isUseJwtSubject() {
		return useJwtSubject;
	}

	public void setUseJwtSubject(boolean useJwtSubject) {
		this.useJwtSubject = useJwtSubject;
	}

	public String getDefaultValueWhenNull() {
		return defaultValueWhenNull;
	}
	public void setDefaultValueWhenNull(String defaultValueWhenNull) {
		this.defaultValueWhenNull = defaultValueWhenNull;
	}
	public Scope getScope() {
		return scope;
	}
	public void setScope (String scope) {
		setScopeEnum(Scope.fromValue(scope));
	}
	
	public void setScopeEnum(Scope scope) {
		this.scope = scope;
	}
	
	public List<FieldValidation> getFieldValidations() {
		return fieldValidations;
	}
	public void setFieldValidations(List<FieldValidation> fieldValidations) {
		this.fieldValidations = fieldValidations;
	}
	public void addFieldValidation(FieldValidation queryParamValidation) {
		getFieldValidations().add(queryParamValidation);
	}

	public boolean isOmitFromSddLookupQuery() {
		return omitFromSddLookupQuery;
	}

	public void setOmitFromSddLookupQuery(boolean omitFromSddLookupQuery) {
		this.omitFromSddLookupQuery = omitFromSddLookupQuery;
	}

}
