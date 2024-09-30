package net.sf.minuteProject.utils.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.minuteProject.configuration.bean.*;
import net.sf.minuteProject.configuration.bean.model.statement.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.minuteProject.configuration.bean.enumeration.Cardinality;
import net.sf.minuteProject.configuration.bean.enumeration.Scope;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ConnectionUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.TableUtils;

public class QueryUtils {

	private static final String QUESTION_MARK = "?";
	private static final String ELLIPSIS = "...";
	private static final String QUESTION_IN_MARK = QUESTION_MARK+ELLIPSIS;
	private static Logger logger = Logger.getLogger(QueryUtils.class);

	public static boolean isBackend(Query query) {
		return Scope.BACKEND==query.getQueryScope();
	}
	public static Model getModel(Query query) {
		return query.getQueries().getStatementModel().getModel();
	}
	public static QueryParams getOutputParams(Query query) throws MinuteProjectException {
		DataModel dataModel = getModel(query)//query.getQueries().getStatementModel().getModel()
				.getDataModel();
		Connection connection = ConnectionUtils.getConnection(dataModel);
		if (connection != null) {
			if (!query.getQueryParams().hasOutputParam() && !query.isWrite()) {
				
				try {
					return getOutputParams(connection, query);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new MinuteProjectException("Query Not working "+query,"QUERY_NOT_WORKING");
				}
			} else {
				String q = getFullQuerySample(query);
				try {
					return getOutputParamsFromCallableStatement(connection, query.getQueryParams(), q, dataModel.getDatabase());
				} catch (SQLException e) {
					e.printStackTrace();
					throw new MinuteProjectException("Query Not working "+query,"QUERY_NOT_WORKING");
				}
				//CallableStatement callableStatement = getCallableStablementFromQuerySample(query);
			}
		}
		return null;
	}

	private static QueryParams getOutputParams(Connection connection, Query query) throws SQLException {
		String q = getFullQuerySample(query);

		try (
				PreparedStatement prest = connection.prepareStatement(q);
				){
			ResultSet rs = prest.executeQuery();
			return getQueryParams(rs.getMetaData());
		} catch (SQLException e) {
			logger.error("error executing query : '"+query.getName()+"'\nSQL:\n"+q);
			logger.error("error sql for query : '"+query.getName()+"'\nError:\n"+e.getMessage());
			return new QueryParams();
		}
	}
	
	private static QueryParams getOutputParamsFromCallableStatement(Connection connection, QueryParams queryParams, String query,
			Database database) throws SQLException {
//		CallableStatement callableStatement = connection.prepareCall(query);
		//callableStatement.execute(createSP);
		
		//TODO should not be last only
		//instead of using sample query
		//TODO callableStatement.executeUpdate();
		/*
		 * int i = 1;
		for (QueryParam queryParam : queryParams.getQueryParams()) {
			if (queryParam.isOutputParam()) {				
				callableStatement.registerOutParameter(i, ConvertUtils.getTypeTypeFromUMLType(queryParam.getType()));
				i++;
			}
		}
		*/

		QueryParams queryParams2 = new QueryParams();
		for (QueryParam queryParam : queryParams.getQueryParams()) {
			if (queryParam.isOutputParam()) {			
				queryParams2.addQueryParam(queryParam);
			}
		}
		return queryParams2;
	}

	private static QueryParams getQueryParams(ResultSetMetaData metaData) throws SQLException {
		QueryParams queryParams = new QueryParams();
		queryParams.setQueryParams(getQueryParamsList(metaData));
		return queryParams;
	}

	private static List<QueryParam> getQueryParamsList(ResultSetMetaData metaData) throws SQLException {
		List<QueryParam> list = new ArrayList<QueryParam>();
		int size = metaData.getColumnCount();
		for (int i = 1; i < size+1; i++) {
			list.add(getQueryParam(metaData, i));
		}
		return list;
	}

	private static QueryParam getQueryParam(ResultSetMetaData metaData, int i) throws SQLException {
		QueryParam qp = new QueryParam();
		String columnName = metaData.getColumnLabel(i);// jdbc driver 4 https://bugs.mysql.com/bug.php?id=88949 metaData.getColumnName(i);
		qp.setName(columnName);
		qp.setSize(metaData.getColumnDisplaySize(i));
		int scale = metaData.getScale(i);
		qp.setScale(scale);
		String columnTypeName = metaData.getColumnTypeName(i);
//		if (columnTypeName.equals("DATE")) {
//			if (scale>0) {
//				qp.setType("TIMESTAMP");
//			} else {
//				qp.setType(columnTypeName);
//			}
//		} else {
//			qp.setType(columnTypeName);
//		}
		qp.setType(columnTypeName);
		return qp;
	}

	public static String getFullQueryQuestionMark(Query<QueryModel> query) {
		return StringUtils.replace(getQueryQuestionMark(query), "\n", " ");
	}
	
	public static String getQueryBodyQuestionMark(Query<QueryModel> query) {
		return StringUtils.replace(getQueryBodyQuestionMarkDefaultAndPagination(query), "\n", " ");
	}
	
	private static String getQueryBodyQuestionMarkDefaultAndPagination(Query<QueryModel> query) {
		if (query==null || query.getQueryBody()==null) return "";
		String queryRaw = query.getQueryBody().getValue();
		//Decorate with pagination if needed
		if (query.hasPagination()) {
			//decorate string
			String pagination_prefix = PaginationUtils.getPaginationFilterPrefix(query.getQueryPagination());
			String pagination_suffix = PaginationUtils.getPaginationFilterSuffix(query.getQueryPagination());
			queryRaw = pagination_prefix + queryRaw + pagination_suffix;
		}
		return queryRaw;
	}
	
	public static String getQueryQuestionMark(Query<QueryModel> query) {
		String queryRaw = getQueryBodyQuestionMarkDefaultAndPagination(query);
		//Decorate with pagination if needed
//		if (query.hasPagination()) {
//			//add filters
//			PaginationUtils.addQueryParams(query);
//			query.getQueryParams().resetFlatQueryParams();
//		}
		//1 get query body
		//sb.append(queryRaw);
		//for each query where reference
		
		// as a simplification it is considered that there is just one 'where' possible where the filter
		// are set: so when the first filter with connection word 'where' set the other are 'and'
		boolean isWhereDone = false;
		for (QueryFilter filter : query.getQueryFilters()) {
			//  get query where string with question mark
			//  append (where or and) to query
			final String name = "$"+filter.getName();
			final String value = filter.getValue();
			final String replacement = filter.isOmitFromSddLookupQuery()?"":queryAndWhere(filter, isWhereDone)+value;
			if (!StringUtils.isEmpty(name) && queryRaw.contains(name)) {
				queryRaw = StringUtils.replace(queryRaw, name, replacement);
			} else {
				queryRaw = queryRaw + replacement;
			}
			isWhereDone = true;
		}
		for (QueryChunk chunk : query.getQueryChunks()) {
			//  get query where string with question mark
			//  append (where or and) to query
			final String name = "$"+chunk.getName();
			final String replacement = getChunkReplacement (chunk);
			if (!StringUtils.isEmpty(name) && queryRaw.contains(name)) {
				queryRaw = StringUtils.replace(queryRaw, name, replacement);
			}
			
		}
		return queryRaw;
	}
	
	private static String getChunkReplacement(QueryChunk chunk) {
		return chunk.getSampleValue();
	}

	private static String queryAndWhere(QueryFilter queryWhere, boolean isWhereDone) {
		if (queryWhere.getConnectWord()==QueryFilter.AndWhere.NONE) return "";
		return " "+((isWhereDone)?QueryFilter.AndWhere.AND.toString():queryWhere.getConnectWord().toString())+" ";
	}

	public static String getFullQuerySample(Query query) {
		String querySt = getQueryQuestionMark(query);

		List<QueryParam> samples = getQueryParamAndFilters (query);
		int samplesSize = samples.size();
		int queryArgSize = StringUtils.countMatches(querySt, QUESTION_MARK);
		assert (samplesSize==queryArgSize);
		//TODO loop on queryParams, convert ? into unique ?1?
		int index = 1;
		for (QueryParam qp : samples) {
			if (qp.isOutputParam()) {
				index++;
			} else {
				if (!qp.isOmitFromSddLookupQuery()) {
					querySt = replaceArgIndexWith(querySt, index, qp.getSample());
				}
			}
			
		}
		return querySt;
	}

	private static String replaceArgIndexWith(String text, int index, String value) {
		//TODO get ? at index position to replace
		int charOrdinalIndex = StringUtils.ordinalIndexOf(text, QUESTION_MARK, index);
		boolean isIn = isInAtIndex(text, charOrdinalIndex);
		text = text.substring(0,charOrdinalIndex)+value+text.substring(isIn?charOrdinalIndex+4:charOrdinalIndex+1);
		return text;
	}

	private static boolean isInAtIndex(String text, int index) {
		return (text.substring(index).startsWith(QUESTION_IN_MARK)) ? true:false;
	}
	
	private static boolean isIn(String text) {
		int i = text.indexOf(QUESTION_MARK);
		if (i==-1)
			return false;
		int j = text.indexOf(QUESTION_IN_MARK);
		if (j==-1)
			return false;
		return i==j;
	}

	private static List<QueryParam> getQueryParamAndFilters(Query<QueryModel> query) {
		List<QueryParam> list = new ArrayList<QueryParam>();
		if (query.getQueryParams() != null) {
			addQueryParamFilters(list, query.getQueryParams());
		}
		//TODO append query where
		for (QueryFilter filter : query.getQueryFilters()) {
			if (filter.getQueryParams() != null) {
				addQueryParamFilters(list, filter.getQueryParams(), filter);
			}
		}
		return list;
	}
	
	
	private static void addQueryParamFilters(List<QueryParam> list, QueryParams params) {
		for (QueryParam qp : params.getFlatQueryParams(false)) {
			list.add(qp);
			if (qp.isOutputParam()) {
				qp.setSample("?");
			}
		}
	}
	
	private static void addQueryParamFilters(List<QueryParam> list, QueryParams params, QueryFilter filter) {
		for (QueryParam qp : params.getFlatQueryParams(false)) {
			list.add(qp);
			qp.setOmitFromSddLookupQuery(filter.isOmitFromSddLookupQuery());
			if (qp.isOutputParam()) {
				qp.setSample("?");
			}
		}
	}

	public static QueryParams getInputParams(Query query) {
		return query.getQueryParams();
	}

	public static List<Column> getInputCompositeFull(Composite composite) {
		List<Column> list = new ArrayList<>();
		for (CompositeQueryElement q : composite.getInputComposite().getQueries()) {
			Query query = q.getQuery();
			for (Column column:query.getInputBean().getColumns()) {
				list.add(column);
			}
		}
		return list;
	
	}
	
	public static Map<String,List<Column>> getInputCompositeDistinct(Composite composite) {
		Map<String,List<Column>> map = new HashMap<String, List<Column>>();
		for (CompositeQueryElement q : composite.getInputComposite().getQueries()) {
			Query query = q.getQuery();
			for (Column column:query.getInputBean().getColumns()) {
				//TODO check that the type of the column is the same, not only the name
				List<Column> list = map.get(column.getName());
				if (list==null)
					list = new ArrayList<Column>();
				list.add(column);
				map.put(column.getName(), list);
			}
		}
		return map;
	}
	
	public static boolean isChartLayout(Query query) {
		return "dashboard".equals(query.getType());
	}
	
	public static boolean isColumnChart(Query query) {
		return "column-chart".equals(query.getCategory());
	}	
	
	public static boolean isPieChart(Query query) {
		return "pie-chart".equals(query.getCategory());
	}
	
	public static boolean isBarChart(Query query) {
		return "bar-chart".equals(query.getCategory());
	}
	
	public static boolean isChart(Query query) {
		return isPieChart(query) || isBarChart(query) || isColumnChart(query);
	}
	
	public static Column getOutputBeanDimension(Query query, int i) {
		if (query.getOutputBean().getColumnCount()>=i+1)
			return query.getOutputBean().getColumn(i);
		return null;
	}
	
	public static List<Column> getOutputBeanDimension(Query query) {
		List<Column> dimensions = new ArrayList<Column>();
		int cpt=0;
		for(Column column : query.getOutputBean().getColumns()) {
			if (cpt>0) {
				dimensions.add(column);
			}
			cpt++;
		}
		return dimensions;
	}

	public static Column getOutputBeanValue(Query query) {
		if (query.getOutputBean().getColumns().length==0)
			return null;
		return query.getOutputBean().getColumn(query.getOutputBean().getColumnCount()-1);
	}
	
	public static String getColumnVariable(Query query, int i) {
		Table table = query.getOutputBean();
		if (table.getColumns().length>i){
			return FormatUtils.getJavaNameVariable(table.getColumn(i).getName());
		}
		if (table.getColumns().length>0){
			return FormatUtils.getJavaNameVariable(table.getColumn(i-1).getName());
		}
		return "no column index "+i+"of query input bean!";
	}
	

	public static String getColumnVariable(Query query, String name) {
		Column column = getColumn(query, name);
		if (column!=null) {
			return FormatUtils.getJavaNameVariable(column.getName());
		}
		return "Column "+name+" not found for query "+query.getName();
	}
	
	public static Column getColumn(Query query, String name) {
		Table table = query.getOutputBean();
		return ColumnUtils.getColumn(table, name);
	}
	
	public static Column getInputBeanColumn(Query query, String name) {
		Table table = query.getInputBean();
		return ColumnUtils.getColumn(table, name);
	}
	
	public static String getColumnTextVariable(Query query, String columnName) {
		if (query==null)
			return "QUERY is null!";
		return getColumnVariable(query, columnName);
	}
	public static String getColumnTextVariable(Query query) {
		if (query==null)
			return "QUERY is null!";
		return getColumnVariable(query, 1);
	}
	
	public static String getColumnValueVariable(Query query, String columnName) {
		if (query==null)
			return "QUERY is null!";
		return getColumnVariable(query, columnName);
	}
	public static String getColumnValueVariable(Query query) {
		if (query==null)
			return "QUERY is null!";
		return getColumnVariable(query, 0);
	}
	
	public static String getContentType(Query query) {
		return query.getContentType();
	}
	
	public static boolean isAdminContentType(Query query) {
		return query.getContentType()!=null 
				&& (TableUtils.masterDataContentType.equals(query.getContentType())
					|| TableUtils.referenceDataContentType.equals(query.getContentType()));
	}
	
	public static Query getQueryByIdOrName(Model model, String queryId) {
		return model.getStatementModel().getQueryByIdOrName(queryId);
	}
	
	public static Query getQueryByIdOrName(Application application, String queryId) {
		for (Model model : application.getModels()) {
			Query query = model.getStatementModel().getQueryByIdOrName(queryId);
			if (query!=null)
				return query;
		}
		return null;
	}
	
	public static String getDescription (Query query) {
		String description = query.getDescription();
		if (description!=null)
			return description;
		return "perform "+query.getName();
	}
	
	public static int getQueryPositionFromOutputbeanPosition (Query query, int startPosition) {
		int i = 1;
		int nbOfOutputParams = 0;
		for (QueryParam queryParam : query.getQueryParams().getQueryParams()) {
			if (queryParam.isOutputParam()) {
				nbOfOutputParams ++; 
				if (nbOfOutputParams==startPosition)
					return i;
			}
			i++;
		}
		return i;
	}
	
	public static boolean isToGenerateBasedVelocityRequired(Template template, GeneratorBean bean) {
		if (bean instanceof Query) {
			Query query = (Query) bean;
			List<QueryScheduler> list = query.getQuerySchedulers();
			return list.stream()
				.anyMatch(u -> u.getReportTemplateType().equals("velocity"));
		}
		return false;
	}
	
	public static boolean isToGenerateBasedRestStackRequired(Template template, GeneratorBean bean) {
		if (bean instanceof Query) {
			Query query = (Query) bean;
			return query.getQueryScope() == Scope.ALL_STACKS;
		}
		return false;
	}
	
	public static boolean isToGenerateBasedOnCardinalityMany(Template template, GeneratorBean bean) {
		if (bean instanceof Table) {
			Table table = (Table) bean;
			if (table.isScalar()) return false;
			return table.getResultCardinality() == Cardinality.MANY;
		}
		return false;
	}
	
	public static boolean isToGenerateBasedOnScalar(Template template, GeneratorBean bean) {
		if (bean instanceof Table) {
			Table query = (Table) bean;
			return query.isScalar();
		}
		return false;
	}
	
	public static boolean isToGenerateBasedOnNotScalar(Template template, GeneratorBean bean) {
		return !isToGenerateBasedOnScalar(template, bean);
	}

	
	public static boolean hasInputBeanAtLeastOneVisibleColumn(Table table) {
		return Arrays.stream(table.getColumns()).filter(c -> !c.isImplicit()).findFirst().isPresent();
	}

	public static QueryParam getFirstParam(QueryParams queryParams) {
		return queryParams.getQueryParams().get(0);
	}

	public static boolean isCache(Query query) {
		return query.isCache() ||
				TableUtils.referenceDataContentType.equals(query.getContentType()) ||
				TableUtils.masterDataContentType.equals(query.getContentType());
	}

	public static Table getQueryInputTable(StatementModel statementModel, String name) {
		final Query query = statementModel.getQueryByName(name);
		return query.getInputBean();
	}

	public static boolean hasVelocityPostProcessor(Query query) {
		List<QueryPostProcessor> qpp = query.getQueryPostProcessors();
		return qpp.stream()
				.anyMatch(u -> u.getType().equalsIgnoreCase("velocity"));
	}

	public static boolean hasVelocityRendition(Application application) {
		return application.getModels().stream()
				.flatMap(u -> u.getStatementModel().getQueries().getQueries().stream())
				.anyMatch(QueryUtils::hasVelocityPostProcessor)
				;
	}

	public static boolean hasVelocityRendition(Model model) {
		return model.getStatementModel().getQueries().getQueries().stream()
				.anyMatch(QueryUtils::hasVelocityPostProcessor)
				;
	}
}
