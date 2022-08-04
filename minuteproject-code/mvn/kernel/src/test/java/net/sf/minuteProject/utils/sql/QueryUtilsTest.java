package net.sf.minuteProject.utils.sql;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryBody;
import net.sf.minuteProject.configuration.bean.model.statement.QueryFilter;
import net.sf.minuteProject.configuration.bean.model.statement.QueryPagination;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParam;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParams;

public class QueryUtilsTest {

	public static final String query1Jdbc = "SELECT A, B from T where C = ? and D = ?";
	public static final String query1Full = "SELECT A, B from T where C = 1 and D = 'test'";
	public static final String query1SampleParam1 = "1";
	public static final String query1SampleParam2 = "'test'";
	public static final String query1Param1Type = "int";
	public static final String query1Param2Type = "string";
	
	//
	public static final String query1Filter1 = "E = ?";
	public static final String query1Filter1Param1Type = "string";
	public static final String query1Filter1Param1Sample = "'e'";
	public static final String query1Filter1Full = "E = 'e'";
	
	//public static final String query1JdbcFilter1 = query1Jdbc + " "+query1Filter1;
	public static final String query1FullFilter1 = query1Full + " AND "+query1Filter1Full;
	
	//
	public static final String query1Filter2 = "(F = ? or G = ?)";
	public static final String query1Filter2Param1Type = "string";
	public static final String query1Filter2Param2Type = "string";
	public static final String query1Filter2Param1Sample = "'f'";
	public static final String query1Filter2Param2Sample = "'g'";
	public static final String query1Filter2Full = "(F = 'f' or G = 'g')";
	
	public static final String query1JdbcFilter2 = query1Jdbc + " "+query1Filter2;
	public static final String query1FullFilter2 = query1Full + " AND "+query1Filter2Full;
	
	//
	public static final String query2Jdbc = "SELECT A, B from T where C = 'c' $filter1 order by A desc";
	public static final String query2Full = "SELECT A, B from T where C = 'c'  AND H = 'h' order by A desc";
	
	public static final String query2FilterName1 = "filter1";
	public static final String query2FilterValue1 = "H = ?";
	public static final String query2Filter1Param1Type = "string";
	public static final String query2Filter1Param1Sample = "'h'";
	
	public static final String query3Jdbc = "SELECT A, B from T";
	public static final String query3Full_1Filter = "SELECT A, B from T WHERE (F = 'f' or G = 'g')";
	public static final String query3Full_2Filter = "SELECT A, B from T WHERE (F = 'f' or G = 'g') AND H = 'h'";
	
	public static final String query4Jdbc = "SELECT A, B from T WHERE (F = ? or G = ?)";
	public static final String query4Full_FilterWithDuplicate = "SELECT A, B from T WHERE (F = 'ff' or G = 'ff')";
	public static final String query4ParamType = "string";
	public static final String query4ParamSample = "'ff'";
	
	public static final String queryInJdbc = "SELECT A, B from T WHERE F in (?...)";
	public static final String queryInFull = "SELECT A, B from T WHERE F in ('ff','ee')";
	public static final String queryInParamType = "string";
	public static final String queryInParamSample = "'ff','ee'";

	
	public static final String queryRegexJdbc = "SELECT A, B from T WHERE F regexp ?...";
	public static final String queryRegexFull = "SELECT A, B from T WHERE F regexp \",('2012'|'2015'),\"";
	public static final String queryRegexParamType = "string";
	public static final String queryRegexParamSample = "\",('2012'|'2015'),\"";
	public static final String queryRegexStart = "\",(";
	public static final String queryRegexEnd = "),\"";
	public static final String queryRegexParamWrapper = "'";
	public static final String queryRegexParamSeparator = "|";
	
	public static final String queryMultiParamJdbc = "SELECT A, B from T WHERE F in (?...) and C = ? and E = ?";
	public static final String queryMultiParam2Jdbc = "SELECT A, B from T WHERE F in (?1...) and C = ?2 and E = ?3";
	public static final String queryMultiParamFull = "SELECT A, B from T WHERE F in (2012,2015) and C = 'xx' and E = 'yy'";
	public static final String queryParam1Type = "string";
	public static final String queryParam1Sample = "2012,2015";	
	public static final String queryParam2Type = "string";
	public static final String queryParam2Sample = "'xx'";	
	public static final String queryParam3Type = "string";
	public static final String queryParam3Sample = "'yy'";
	
	public static final String querySampleStoreProcOutputParamJdbc = "call key_config_persist(?, ?)";
	public static final String querySampleStoreProcOutputParamFull = "call key_config_persist(?, 'EVENT')";
	
	public static final String query1JdbcPaginationMysqlLimit = "SELECT A, B from T where C = ? and D = ?";
	
	public static final String query1JdbcPaginationMysqlLimitFull = "SELECT A, B from T where C = 'c' and D = 'd' limit 1 offset 1";
	public static final String query1JdbcPaginationMysqlLimitOffsetFull = "SELECT A, B from T where C = 'c' and D = 'd' limit 1 offset 1";
	
	Query query1;
	
	@Before
	public void setUp() {
		query1=new Query();
		query1.setQueryBody(getQueryBody(query1Jdbc));
		query1.setQueryParams(getQueryParams(getQueryParam1(), getQueryParam2()));
	}
	
	private QueryParams getQueryParams(QueryParam ... filter) {
		QueryParams queryParams = new QueryParams();
		queryParams.setQueryParams(getQueryParamList(filter));
		return queryParams;
	}

	private List<QueryParam> getQueryParamList(QueryParam ... filter) {
		List<QueryParam> list = new ArrayList<QueryParam>();
		for (QueryParam queryParam : filter) {
			list.add(queryParam);
		}
		return list;
	}

	private QueryParam getQueryParam(String type, String sample) {
		return getQueryParam(type, sample, null);
	}
	
	private QueryParam getQueryParam(String type, String sample, Integer index) {
		QueryParam queryParam = new QueryParam();
		queryParam.setId(sample);
		queryParam.setType(type);
		queryParam.setSample(sample);
		queryParam.setIndex(index);
		return queryParam;
	}
	
	private QueryParam getQueryParamByRefId(String refid) {
		QueryParam queryParam = new QueryParam();
		queryParam.setRefid(refid);
		return queryParam;
	}
	
	private QueryParam getQueryParam1() {
		return getQueryParam(query1Param1Type, query1SampleParam1);
	}

	private QueryParam getQueryParam2() {
		return getQueryParam(query1Param2Type, query1SampleParam2);
	}

	private QueryBody getQueryBody(String jdbc) {
		QueryBody qb = new QueryBody();
		qb.setValue(jdbc);
		return qb;
	}

	private Query getQueryWithFilter(String jdbc, String name, String filter,
			String filterparamtype, String filterparamsample) {
		Query query = getQuery(jdbc);
		query.addQueryFilter(getQueryFilter(name, filter, filterparamtype, filterparamsample, QueryFilter.AndWhere.AND));
		return query;
	}

	private Query getQuery(String jdbc) {
		Query query=new Query();
		query.setQueryBody(getQueryBody(jdbc));
		return query;
	}

	private QueryFilter getQueryFilter(String name, String filter,String filterparamtype,
			String filterparamsample, QueryFilter.AndWhere andWhere) {
		QueryFilter queryFilter = new QueryFilter();
		queryFilter.setName(name);
		queryFilter.setValue(filter);
		queryFilter.setConnectWord(andWhere);
		queryFilter.setQueryParams(getQueryParams(getQueryParam(filterparamtype,filterparamsample)));
		return queryFilter;
	}
	//
	@Test
	public void simpleFullQuery() {
		String s = QueryUtils.getFullQuerySample(query1);
		assertTrue(s +" but expect :"+query1Full, query1Full.equals(s));
	}
	
	@Test
	public void queryWithImplicitFilter() {
		//when query 2
		Query query2 = getQueryWithFilter(query1Jdbc, "", query1Filter1, query1Filter1Param1Type, query1Filter1Param1Sample);
		query2.getQueryParams().addQueryParam(getQueryParam1());
		query2.getQueryParams().addQueryParam(getQueryParam2());
		String s = QueryUtils.getFullQuerySample(query2);
		assertTrue(s+" but expect :"+query1FullFilter1, query1FullFilter1.equals(s));
	}
	
	@Test
	public void queryWithImplicitFilter2() {
		Query query = getQueryWithFilter(query1Jdbc, "", query1Filter2, query1Filter2Param1Type, query1Filter2Param1Sample);
		query.getQueryParams().addQueryParam(getQueryParam1());
		query.getQueryParams().addQueryParam(getQueryParam2());
		//query.getQueryFilters().get(0).g
		((QueryFilter)query.getQueryFilters().get(0)).getQueryParams().addQueryParam(getQueryParam(query1Filter2Param2Type, query1Filter2Param2Sample));
		String s = QueryUtils.getFullQuerySample(query);
		assertTrue(s+" but expect :"+query1FullFilter2, query1FullFilter2.equals(s));
	}

	
	@Test
	public void query2WithExplicitFilter() {
		//StringUtils.replace(query2Jdbc, query2FilterName1, query2FilterValue1);
		Query query4 = getQueryWithFilter(query2Jdbc, query2FilterName1, query2FilterValue1, query2Filter1Param1Type, query2Filter1Param1Sample);
		String s = QueryUtils.getFullQuerySample(query4);
		assertTrue(s +" but expect :"+query2Full, query2Full.equals(s));
	}
	
	
	@Test
	public void query2WithExplicitWhereFilter() {
		//given
		Query query = getQuery(query3Jdbc);
		query.addQueryFilter(getQueryFilter("", query1Filter2, query1Filter2Param1Type, query1Filter2Param1Sample, QueryFilter.AndWhere.WHERE));
		((QueryFilter)query.getQueryFilters().get(0)).getQueryParams().addQueryParam(getQueryParam(query1Filter2Param2Type, query1Filter2Param2Sample));
		// when
		String s = QueryUtils.getFullQuerySample(query);
		//then
		assertTrue(s +" but expect :"+query3Full_1Filter, query3Full_1Filter.equals(s));
	}
	
	@Test
	public void query2WithExplicitWhereFilterAnd2Filters() {
		//given
		Query query = getQuery(query3Jdbc);
		query.addQueryFilter(getQueryFilter("", query1Filter2, query1Filter2Param1Type, query1Filter2Param1Sample, QueryFilter.AndWhere.WHERE));
		((QueryFilter)query.getQueryFilters().get(0)).getQueryParams().addQueryParam(getQueryParam(query1Filter2Param2Type, query1Filter2Param2Sample));
		query.addQueryFilter(getQueryFilter("", query2FilterValue1, query2Filter1Param1Type, query2Filter1Param1Sample, QueryFilter.AndWhere.WHERE));
		// when
		String s = QueryUtils.getFullQuerySample(query);
		//then
		assertTrue(s +" but expect :"+query3Full_2Filter, query3Full_2Filter.equals(s));
	}
	
	@Test
	public void queryWithDuplicateParams() {
		//given
		Query query = getQuery(query4Jdbc);
		QueryParams queryParams = new QueryParams();
		query.setQueryParams(queryParams);
		query.getQueryParams().addQueryParam(getQueryParam(query4ParamType, query4ParamSample));
		query.getQueryParams().addQueryParam(getQueryParamByRefId(query4ParamSample));
		// when
		String s = QueryUtils.getFullQuerySample(query);
		//then
		assertTrue(s +" but expect :"+query4Full_FilterWithDuplicate, query4Full_FilterWithDuplicate.equals(s));
	}
	
	@Test
	public void queryRegexParam() {
		//given
		Query query = getQuery(queryRegexJdbc);
		QueryParams queryParams = new QueryParams();
		query.setQueryParams(queryParams);
		QueryParam queryParam = getQueryParam(queryRegexParamType, queryRegexParamSample);
		queryParam.setRegexEnd(queryRegexEnd);
		queryParam.setRegexStart(queryRegexStart);
		queryParam.setRegexParamSeparator(queryRegexParamSeparator);
		queryParam.setRegexParamWrapper(queryRegexParamWrapper);
		query.getQueryParams().addQueryParam(queryParam);
		// when
		String s = QueryUtils.getFullQuerySample(query);
		//then
		assertTrue(s +" but expect :"+queryRegexFull, queryRegexFull.equals(s));
	}
	
	@Test
	public void queryInParams() {
		//given
		Query query = getQuery(queryInJdbc);
		QueryParams queryParams = new QueryParams();
		query.setQueryParams(queryParams);
		query.getQueryParams().addQueryParam(getQueryParam(queryInParamType, queryInParamSample));
		// when
		String s = QueryUtils.getFullQuerySample(query);
		//then
		assertTrue(s +" but expect :"+queryInFull, queryInFull.equals(s));
	}

	
	@Test
	public void queryWithMultiParams() {
		//given
		Query query = getQuery(queryMultiParamJdbc);
		query.getQueryParams().addQueryParam(getQueryParam(queryParam1Type, queryParam1Sample));
		query.getQueryParams().addQueryParam(getQueryParam(queryParam2Type, queryParam2Sample));
		query.getQueryParams().addQueryParam(getQueryParam(queryParam3Type, queryParam3Sample));
		// when
		String s = QueryUtils.getFullQuerySample(query);
		//then
		assertTrue(s +" but expect :"+queryMultiParamFull, queryMultiParamFull.equals(s));
	}

	@Test
	public void querySampleStoreProcOutputParam() {
		//given
		Query query = getQuery(querySampleStoreProcOutputParamJdbc);
		QueryParam qp = getQueryParam("int", "1", 1);
		qp.setIsOutputParam(true);
		query.getQueryParams().addQueryParam(qp);
		query.getQueryParams().addQueryParam(getQueryParam("string", "'EVENT'", 2));

		// when
		String s = QueryUtils.getFullQuerySample(query);
		//then
		assertTrue(s +" but expect :"+querySampleStoreProcOutputParamFull, querySampleStoreProcOutputParamFull.equals(s));
	}
	
	@Test
	public void paginatedMysqlQueries () {
		Query query = getQuery(query1JdbcPaginationMysqlLimit);
		
		query.getQueryParams().addQueryParam(getQueryParam("string", "'c'", 1));
		query.getQueryParams().addQueryParam(getQueryParam("string", "'d'", 2));
		QueryPagination qp = new QueryPagination();
		//qp.setMaxResult(10);
		query.setQueryPagination(qp);
		
		//TODO qp adds maxResult and offset as (mandatory) queryParams
		//TODO decorates the query regarding the DB (todo oracle, mysql, postgresql)
		// assert 
		String s = QueryUtils.getFullQuerySample(query);
		
		assertTrue(s +" but expect :"+query1JdbcPaginationMysqlLimitFull, query1JdbcPaginationMysqlLimitFull.equals(s));

	}
	
	
	@Test
	public void paginatedOffsetMysqlQueries () {
		Query query = getQuery(query1JdbcPaginationMysqlLimit);
		query.getQueryParams().addQueryParam(getQueryParam("string", "'c'", 1));
		query.getQueryParams().addQueryParam(getQueryParam("string", "'d'", 2));
		QueryPagination qp = new QueryPagination();
//		qp.setMaxResult(10);
//		qp.setOffset(5);
		query.setQueryPagination(qp);
		
		//TODO qp adds maxResult and offset as (mandatory) queryParams
		//TODO decorates the query regarding the DB (todo oracle, mysql, postgresql)
		// assert 
		String s = QueryUtils.getFullQuerySample(query);
		
		assertTrue(s +" but expect :"+query1JdbcPaginationMysqlLimitOffsetFull, query1JdbcPaginationMysqlLimitOffsetFull.equals(s));
		
	}
		/*
	@Test
	public void queryWithMultiParams2() {
		//given
		Query query = getQuery(queryMultiParam2Jdbc);
		query.getQueryParams().addQueryParam(getQueryParam(queryParam1Type, queryParam1Sample, 1));
		query.getQueryParams().addQueryParam(getQueryParam(queryParam2Type, queryParam2Sample, 2));
		query.getQueryParams().addQueryParam(getQueryParam(queryParam3Type, queryParam3Sample, 3));
		// when
		String s = QueryUtils.getFullQuerySample(query);
		//then
		assertTrue(s +" but expect :"+queryMultiParamFull, queryMultiParamFull.equals(s));
	}
	*/
}
	