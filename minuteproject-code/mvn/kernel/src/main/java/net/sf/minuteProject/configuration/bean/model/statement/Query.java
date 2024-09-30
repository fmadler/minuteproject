package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.enrichment.security.SecurityColor;
import net.sf.minuteProject.configuration.bean.enrichment.validation.EntityValidation;
import net.sf.minuteProject.configuration.bean.enumeration.Cache;
import net.sf.minuteProject.configuration.bean.enumeration.Cardinality;
import net.sf.minuteProject.configuration.bean.enumeration.Scope;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.sql.PaginationUtils;
import net.sf.minuteProject.utils.sql.QueryUtils;

public class Query<T extends QueryModel> extends AbstractConfiguration {

	public static final String DUPLICATED_TAG ="<DUPLICATED>";
	private T t;
	
	private Queries queries;
	private QueryBody queryBody;
	private QueryDisplay queryDisplay;
	private QueryPagination queryPagination;
	private List<Field> queryFields;
	private List<QueryFilter> queryFilters;
	private List<QueryScheduler> querySchedulers;
	private List<QueryPostProcessor> queryPostProcessors;
	private List<QueryLink> queryLinks;
	private QueryParams queryParams;
	private QueryParams outputParams;
	private boolean isSet = false;
	private boolean cache = false;
	private Package pack;
	private String type, category, executeLabel;
	private Table tableIn, tableOut;
	private List<Action> actions;
	private List<Action> inputActions;
	private List<QueryChunk> queryChunks;
	private List<QueryPivot> pivots;
	private String packageName;
	private boolean isWrite = false;
	private String contentType;
	private Cache caching = Cache.READ_ONLY;
	private Cardinality resultCardinality = Cardinality.MANY;
	private String secureUserRole;
	private SecurityColor securityColor=new SecurityColor();
	private Scope queryScope = Scope.ALL_STACKS;
	private boolean isScalar = false;
	private boolean pagination = false;
	private boolean paginationAsFilter = false;
	private int paginationSize = 0;
	private List<EntityValidation> entityValidations = new ArrayList<>();
	private String namingConvention;

	public void setQueryModel (T t) {
		this.t = t;
	}
	public QueryModel getQueryModel () {
		if (t==null) {
			if (getQueries().getStatementModel().getModel().hasDataModel())
				return new SqlQueryModel();
			else if (getQueries().getStatementModel().getModel().hasCmisModel()) {
				return new CmisQueryModel();
			}
		}
		return t;
	}
	public QueryParams getInputParams() {
		return QueryUtils.getInputParams(this);
	}

	public QueryParams getOutputParams() {
		if (outputParams == null && !isSet) {
			try {
				outputParams = getQueryModel().getOutputParams(this);//QueryUtils.getOutputParams(this);
			} catch (MinuteProjectException e) {
				isSet = true;
				// TODO log error
			}
			isSet = true;
		}
		return outputParams;
	}

	public Queries getQueries() {
		return queries;
	}

	public void setQueries(Queries queries) {
		this.queries = queries;
	}

	public QueryBody getQueryBody() {
		return queryBody;
	}

	public void setQueryBody(QueryBody queryBody) {
		this.queryBody = queryBody;
	}

	public void setQueryBody(String s) {
		queryBody = new QueryBody();
		queryBody.setValue(s);
	}

	public List<QueryPivot> getPivots() {
		if (pivots==null) {
			pivots = new ArrayList<QueryPivot>();
		}
		return pivots;
	}
	
	public void addQueryPivot(QueryPivot queryPivot) {
		queryPivot.setQuery(this);
		getPivots().add(queryPivot);
	}
	
	public List<QueryChunk> getQueryChunks() {
		if (queryChunks==null) {
			queryChunks = new ArrayList<>();
		}
		return queryChunks;
	}
	
	public void addQueryChunk(QueryChunk queryChunk) {
		getQueryChunks().add(queryChunk);
	}

	public List<QueryLink> getQueryLinks() {
		if (queryLinks==null) {
			queryLinks = new ArrayList<>();
		}
		return queryLinks;
	}

	public void addQueryLink(QueryLink queryLink) {
		getQueryLinks().add(queryLink);
	}

	public List<QueryFilter> getQueryFilters() {
		if (queryFilters==null) {
			queryFilters = new ArrayList<QueryFilter>();
		}
		if (!paginationAsFilter && hasPagination()) {
			QueryFilter maxResultQueryFilter = PaginationUtils.getMaxResultQueryFilter(queryPagination);
			if (maxResultQueryFilter!=null){
				queryFilters.add(maxResultQueryFilter);
			}
			QueryFilter offsetQueryFilter = PaginationUtils.getOffsetQueryFilter(queryPagination);
			if (offsetQueryFilter!=null){
				queryFilters.add(offsetQueryFilter);
			}
			paginationAsFilter = true;
		}
		return queryFilters;
	}

	public void addQueryFilter(QueryFilter queryFilter) {
		if (queryFilter!=null) {
			getQueryFilters().add(queryFilter);
		}
	}

	public void addQueryPostProcessor(QueryPostProcessor queryPostProcessor) {
		if (queryPostProcessor!=null) {
			getQueryPostProcessors().add(queryPostProcessor);
		}
	}
	
	public List<QueryPostProcessor> getQueryPostProcessors() {
		if (queryPostProcessors==null) {
			queryPostProcessors = new ArrayList<>();
		}
		return queryPostProcessors;
	}

	public List<QueryScheduler> getQuerySchedulers() {
		if (querySchedulers==null) {
			querySchedulers = new ArrayList<>();
		}
		return querySchedulers;
	}

	public void addQueryScheduler(QueryScheduler queryScheduler) {
		queryScheduler.setQuery(this);
		getQuerySchedulers().add(queryScheduler);
	}
	
	public List<Field> getQueryFields() {
		if (queryFields==null) {
			queryFields = new ArrayList<>();
		}
		return queryFields;
	}
	
	public void addField(Field field) {
		field.setIsMandatory(true); // input field (not filter) are mandatory
		getQueryFields().add(field);
	}

	public QueryParams getQueryParams() {
		if (queryParams == null)
			queryParams = new QueryParams();
		return queryParams;
	}

	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
		this.queryParams.setQuery(this);
	}

	public Table getInputBean() {
		if (tableIn == null)
			tableIn = getEntityFromDirection(Direction.IN);
		return tableIn;
	}

	public Table getOutputBean() {
		if (tableOut == null)
			tableOut = getEntityFromDirection(Direction.OUT);
		return tableOut;
	}

	// remove duplication
	public Table getEntityFromDirection(Direction dir) {
		Table entity = getEntityRoot(dir);
		if (dir.equals(Direction.IN)) {
			complementValidation(entity);
			complementFields(entity, queryParams);
		}
		if (dir.equals(Direction.OUT)) {
			complementOutputFields(entity);
		}
		return entity;
	}

	private void complementValidation(Table entity) {
		entity.setValidations(getEntityValidations());
	}
	
	// remove duplication
	public Table getEntity(Direction dir) {
		if (Direction.IN.equals(dir))
			return getInputBean();
		return getOutputBean();
	}

	private void complementOutputFields(Table entity) {
		entity.setResultCardinality(resultCardinality);
		entity.setScalar(entity.isScalar());
		entity.setNamingConvention(namingConvention);
	}
	
	private void complementFields(Table table, QueryParams queryParams) {
		List<QueryParam> list = getQueryParams(Direction.IN);
		for (QueryParam queryParam : list) {
			//cannot search on column name in case of duplicated columns
			Column column = ColumnUtils.getColumn(table, queryParam.getName());
			if (column != null) {
				column.setStereotype(queryParam.getStereotype());
				column.setFilterName(queryParam.getQueryParams().getFilterName());
				column.setSessionParamName(queryParam.getSessionParamName());
				column.setQueryParamLink(queryParam.getQueryParamLink());
				column.setDefaultValue(queryParam.getDefaultValueWhenNull());
				column.setIsOutputParam(queryParam.isOutputParam());
				column.setDefaultValue(queryParam.getDefaultValue());
			}
		}
		for (Column column : table.getColumns()) {
			if (ColumnUtils.hasTransientColumnName(column)) {
				column.setHidden(true);
				column.setEditable(false);
				column.setRequired(false);
				column.setTransient(true);
			}
			if (column.getName().contains(DUPLICATED_TAG)) {
				column.setHasBeenDuplicated(true);
				column.setName(column.getName().replace(DUPLICATED_TAG, ""));
			}
		}
	}

	private Table getEntityRoot(Direction dir) {
		org.apache.ddlutils.model.Table table = new org.apache.ddlutils.model.Table();
		
		setTableName(table, dir);
		table.setType(Table.TABLE);
		addColumns(table, dir);

		Table entity = new TableDDLUtils(table);
		getQueryModel().setEntityModelSpecific(this, dir, table, entity);
		entity.setPackage(getPackage());
		
		complementColumn(entity, dir);
		return entity;
	}
	
//	private void setEntityModelSpecific(Queries queries, Direction dir,
//			org.apache.ddlutils.model.Table table, Table entity) {
//		Database database = queries.getStatementModel().getModel().getDataModel().getDatabase();
//		initFieldAndRelationship(dir, database, table);
//		entity.setDatabase(database);
//	}

//	private void initFieldAndRelationship(Direction dir, Database database,
//			org.apache.ddlutils.model.Table table) {
//		if (dir.equals(Direction.IN)) {
//			List<QueryParam> list = getQueryParams(Direction.IN);
//			for (QueryParam queryParam : list) {
//				Entity.assignForeignKey(database, table,
//						queryParam.getLinkField());
//			}
//		}
//	}

	private void setTableName(org.apache.ddlutils.model.Table table,
			Direction dir) {
		String queryparamName = getQueryParams().getName();
		if (dir.equals(Direction.IN) && !StringUtils.isEmpty(queryparamName))
			table.setName(formatTableName(queryparamName));
		else
			table.setName(formatTableName(getName()));
	}

	private String formatTableName(String name) {
		return FormatUtils.getUppercaseUnderscore(name);
	}

	public Package getPackage() {
		return pack;
	}

	public void setPackage(Package pack) {
		this.pack = pack;
	}

	public String getTechnicalPackage(Template template) {
		net.sf.minuteProject.configuration.bean.Package p = getPackage();
		if (p == null) {
			// TODO null when queryParamLink reference a query that is not yet declared...
			Configuration configuration = getQueries().getStatementModel().getModel().getConfiguration();
			for (Model m : configuration.getAllModels()) {
				for (Package pack : m.getStatementModel().getSddPackage().getQueryPackages()) {
					if (pack.getName().equalsIgnoreCase(getPackageName()))
						{
						return pack.getTechnicalPackage(template) + "."
								+ CommonUtils.getSDDPackageName(this);
					}
				}
			}
			return "ERROR_QUERY_PACKAGE_IS_NULL";
		}
		return p.getTechnicalPackage(template) + "." + CommonUtils.getSDDPackageName(this);
	}

	private void addColumns(org.apache.ddlutils.model.Table table, Direction direction) {
		List<QueryParam> list = getQueryParams(direction);
		for (QueryParam queryParam : list) {
/*			if (!queryParam.isLink()) {
				table.addColumn(getColumn(table, queryParam));
			} else {*/
				///table.addColumn(getColumnTransient(queryParam));
				table.addColumn(getColumn(table, queryParam));
			//}
		}
	}

	private void complementColumn(Table table, Direction direction) {
		List<QueryParam> list = getQueryParams(direction);
		//TODO correct double loop error with duplicated columns
		//copy info to duplicate
		for (QueryParam queryParam : list) {
			for (Column column : table.getColumns()) {
				if (column.getName().equalsIgnoreCase(queryParam.getName())) {
					column.setProperties(queryParam.getProperties());
					column.setStereotype(queryParam.getStereotype());
					column.setQueryParamLink(queryParam.getQueryParamLink());
					column.setIsArray(queryParam.isArray());
					column.setScope(queryParam.getScope());
					column.setValidations(queryParam.getFieldValidations());

					column.setImplicit(queryParam.isImplicit());
					column.setUseJwtSubject(queryParam.isUseJwtSubject());
				}
			}
		}
		//only for direction OUT
		if (direction == Direction.OUT) {
			for (Field field : getQueryFields()) {
				for (Column column : table.getColumns()) {
					if (column.getName().equalsIgnoreCase(field.getName())) {			
						column.setIsStructuredArray(field.isStructuredArray());
						column.setSeparatorCharacters(field.getSeparatorCharacters());
						column.setArrayColumns(field.getArrayColumns());
						column.setArrayElementName(field.getArrayElementName());
						column.setArrayColumnsType(field.getArrayColumnsType());
						column.setHidden(field.isHidden());
					}
				}
			}
		}

	}

	public List<QueryParam> getQueryParams(Direction direction) {
		if (Direction.IN.equals(direction)) {
			List<QueryParam> list = new ArrayList<>();
			list.addAll(getInputParams().getFlatQueryParams(true));
			for (QueryFilter filter : getQueryFilters()) {
				QueryParams queryParams2 = filter.getQueryParams();
				if (queryParams2!=null) {
					list.addAll(queryParams2.getFlatQueryParams(false));
				}
			}
			return list;
		}
		if (getOutputParams() != null)
			return getOutputParams().getQueryParams();
		return new ArrayList<QueryParam>();
	}

	private org.apache.ddlutils.model.Column getColumn(org.apache.ddlutils.model.Table table, QueryParam queryParam) {
		org.apache.ddlutils.model.Column column = new org.apache.ddlutils.model.Column();
		column.setName(getColumnName(table, queryParam));
		String type = queryParam.getType();
		if (type==null) type = "string";
		column.setType(convertType(type));
		column.setSize(queryParam.getSizeOrDefault());
		column.setScale(queryParam.getScale());
		column.setDefaultValue(queryParam.getDefaultValue());
		if (ConvertUtils.DB_DECIMAL_TYPE.equals(type)
				&& queryParam.getScale() > 0) {
			column.setType(ConvertUtils.DB_DOUBLE_TYPE);
		}
		column.setPrimaryKey(queryParam.isId()); // cannot be set here
		column.setRequired(queryParam.isMandatory());
		
		if (queryParam.isAliasSet()) {
			column.setJavaName(queryParam.getAlias());// todo add column alias
		}
		return column;
	}
	
	private String getColumnName(org.apache.ddlutils.model.Table table, QueryParam queryParam) {
		if (!queryParam.hasBeenDuplicated())
			return queryParam.getName();
		else {
			return queryParam.getName()+DUPLICATED_TAG;
		}
	}

	private String convertType(String type) {
		return ConvertUtils.getDDLUtilsTypeFromDBType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Action> getActions() {
		if (actions == null)
			actions = new ArrayList<Action>();
		return actions;
	}

	public void addAction(Action action) {
		getActions().add(action);
	}
	
	public List<Action> getInputActions() {
		if (inputActions == null)
			inputActions = new ArrayList<Action>();
		return inputActions;
	}
	
	public void addInputAction(Action action) {
		getInputActions().add(action);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public boolean isWrite() {
		return isWrite;
	}

	public void setWrite(boolean isWrite) {
		this.isWrite = isWrite;
	}

	public String getExecuteLabel() {
		return executeLabel;
	}

	public void setExecuteLabel(String executeLabel) {
		this.executeLabel = executeLabel;
	}	
	
	public QueryDisplay getQueryDisplay() {
		return queryDisplay;
	}

	public void setQueryDisplay(QueryDisplay queryDisplay) {
		queryDisplay.setQuery(this);
		this.queryDisplay = queryDisplay;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	
	public Cardinality getResultCardinality() {
		return resultCardinality;
	}
	
	public void setCard(String cardinality) {
		this.resultCardinality = Cardinality.valueOf(cardinality.toUpperCase());
	}

	public Cache getCaching() {
		return caching;
	}
	public void setCaching(Cache caching) {
		this.caching = caching;
	}

	@Override
	public String toString() {
		return "query [name='"+name+"', id='"+id+"']";
	}
	
	public String getSecureUserRole() {
		return secureUserRole;
	}
	public void setSecureUserRole(String secureUserRole) {
		this.secureUserRole = secureUserRole;
	}
	public SecurityColor getSecurityColor() {
		return securityColor;
	}
	public void setSecurityColor(SecurityColor securityColor) {
		this.securityColor = securityColor;
	}

	public void setScope (String scope) {
		for (Scope qs : Scope.values()) {
			if (qs.name().equalsIgnoreCase(scope)) {
				queryScope = qs;
			}
		}
	}
	public Scope getQueryScope() {
		return queryScope;
	}
	public boolean isScalar() {
		return isScalar;
	}
	public void setScalar(boolean isScalar) {
		this.isScalar = isScalar;
	}
	public boolean isPagination() {
		return pagination;
	}
	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}
	public int getPaginationSize() {
		return paginationSize;
	}
	public void setPaginationSize(int paginationSize) {
		this.paginationSize = paginationSize;
	}

	public QueryPagination getQueryPagination() {
		return queryPagination;
	}
	public void setQueryPagination(QueryPagination queryPagination) {
		queryPagination.setQuery(this);
		this.queryPagination = queryPagination;
	}

	public boolean hasPagination() {
		return queryPagination!=null;
	}

	//validation
	public void addEntityValidation(EntityValidation queryValidation) {
		getEntityValidations().add(queryValidation);
	}
	public List<EntityValidation> getEntityValidations() {
		return entityValidations;
	}
	public void setEntityValidations(List<EntityValidation> entityValidations) {
		this.entityValidations = entityValidations;
	}
	
	public boolean hasQueryChunkWithParams() {
		return getQueryChunks().stream().anyMatch(u -> u.getQueryChunkParams().size()>0);
	}

	public String getNamingConvention() {
		return namingConvention;
	}

	public void setNamingConvention(String namingConvention) {
		this.namingConvention = namingConvention;
	}
}
