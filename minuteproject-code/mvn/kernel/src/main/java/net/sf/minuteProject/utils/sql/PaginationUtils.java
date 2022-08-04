package net.sf.minuteProject.utils.sql;

import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryFilter;
import net.sf.minuteProject.configuration.bean.model.statement.QueryFilter.AndWhere;
import net.sf.minuteProject.configuration.bean.model.statement.QueryModel;
import net.sf.minuteProject.configuration.bean.model.statement.QueryPagination;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParam;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParams;

public class PaginationUtils {
	
	private static final String offset_string = "___offset___";
	private static final String max_result    = "___max_result___";
	
	public static String getPaginationPrefix(QueryPagination queryPagination) {
		return getPaginationPrefix(queryPagination, "MYSQL");
	}
	
	public static String getPaginationFilterPrefix(QueryPagination queryPagination) {
		return getPaginationFilterPrefix(queryPagination, "MYSQL");
	}
	
	public static String getPaginationSuffix(QueryPagination queryPagination) {
		return getPaginationSuffix(queryPagination, "MYSQL");
	}
	
	public static String getPaginationFilterSuffix(QueryPagination queryPagination) {
		return getPaginationFilterSuffix(queryPagination, "MYSQL");
	}
	
	public static String getPaginationPrefix(QueryPagination queryPagination, String databaseType) {
		//FOR MYSQL
		return "";
	}
	
	public static String getPaginationFilterPrefix(QueryPagination queryPagination, String databaseType) {
		//FOR MYSQL
		return "";
	}
	
	public static String getPaginationSuffix(QueryPagination queryPagination, String databaseType) {
		StringBuilder sb = new StringBuilder();
//		if (queryPagination.getMaxResult()>0) {
			sb.append(getMaxResultChunk(queryPagination));
//		}
//		if (queryPagination.getOffset()>0) {
			sb.append(getOffsetChunk(queryPagination));
//		}
		return sb.toString();
	}

	private static String getOffsetChunk(QueryPagination queryPagination) {
		return queryPagination.getOffsetParam()+" ?";
	}

	private static String getMaxResultChunk(QueryPagination queryPagination) {
		return queryPagination.getMaxResultParam()+" ?";
	}
	
	
	public static String getPaginationFilterSuffix(QueryPagination queryPagination, String databaseType) {
		StringBuilder sb = new StringBuilder();
//		if (queryPagination.getMaxResult()>0) {
			sb.append(" $"+max_result);
//		}
//		if (queryPagination.getOffset()>0) {
			sb.append(" $"+offset_string);
//		}
		return sb.toString();
	}

	public static void addQueryParams(Query<QueryModel> query) {//QueryPagination queryPagination, QueryParams queryParams) {
		QueryPagination queryPagination = query.getQueryPagination();
		QueryParams queryParams = query.getQueryParams();
		QueryParam maxResultQueryParam = getMaxResultQueryParam(queryPagination);
		if (maxResultQueryParam!=null){
			queryParams.getQueryParams().add(maxResultQueryParam);
		}
		
		QueryParam offsetQueryParam = getOffsetQueryParam(queryPagination);
		if (offsetQueryParam!=null){
			queryParams.getQueryParams().add(offsetQueryParam);
		}
	}

	public static QueryFilter getOffsetQueryFilter(QueryPagination queryPagination) {
		QueryParam offsetQueryParam = getOffsetQueryParam(queryPagination);
		
		if (offsetQueryParam!=null) {
			QueryFilter qf = new QueryFilter();
			qf.setConnectWord(AndWhere.NONE);
			
			qf.setName(offset_string);
			qf.setValue(getOffsetChunk(queryPagination));
			QueryParams qps = new QueryParams();
			qps.addQueryParam(offsetQueryParam);
			qf.setQueryParams(qps);
			return qf;
		}
		return null;
	}
	
	public static QueryParam getOffsetQueryParam(QueryPagination queryPagination) {
//		if (queryPagination.getOffset()>0) {
			QueryParam queryParam = new QueryParam();
			queryParam.setName(queryPagination.getOffsetParam());
			queryParam.setType("INTEGER");
//			String sample = queryPagination.getOffset()+"";
			String sample = "1";
			queryParam.setValue(sample);
			queryParam.setSample(sample);
			queryParam.setDefaultValue(queryPagination.getOffsetDefault()!=null?queryPagination.getOffsetDefault()+"":null);
			//queryParams.getQueryParams().add(qf);
			//query.getInputParams().addQueryParam(queryParam);
			return queryParam;
//		}
//		return null;
	}


	public static QueryFilter getMaxResultQueryFilter(QueryPagination queryPagination) {
		QueryParam maxResultQueryParam = getMaxResultQueryParam(queryPagination);
		if (maxResultQueryParam!=null) {
			QueryFilter qf = new QueryFilter();
			qf.setConnectWord(AndWhere.NONE);
			qf.setName(max_result);
			qf.setValue(getMaxResultChunk(queryPagination));
			QueryParams qps = new QueryParams();
			qps.addQueryParam(maxResultQueryParam);
			qf.setQueryParams(qps);
			return qf;
		}
		return null;
		
	}
	
	public static QueryParam getMaxResultQueryParam(QueryPagination queryPagination) {
//		if (queryPagination.getMaxResult()>0) {
			QueryParam queryParam = new QueryParam();
			queryParam.setName(queryPagination.getMaxResultParam());
			queryParam.setType("INTEGER");
//			String sample = queryPagination.getMaxResult()+"";
			String sample = "1";
			queryParam.setValue(sample);
			queryParam.setSample(sample);
			queryParam.setDefaultValue(queryPagination.getMaxResultDefault()!=null?queryPagination.getMaxResultDefault()+"":null);
			//queryParams.getQueryParams().add(qf);
			//queryParams.getQuery().getInputBean().addColumn(column); ?? or queryParams.getQuery().getInputParams().addQueryParam(qf);
			//query.getInputParams().addQueryParam(qf);
			//query.getInputBean().addColumn(column);
			return queryParam;
//		}
//		return null;
	}
}
