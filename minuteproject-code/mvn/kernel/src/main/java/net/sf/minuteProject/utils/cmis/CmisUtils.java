package net.sf.minuteProject.utils.cmis;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.model.cmis.CmisModel;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParam;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParams;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.ConnectionUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.sql.QueryUtils;

import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CmisUtils {
	
	private static Logger logger = Logger.getLogger(CmisUtils.class);
	
	public static QueryParams getOutputParams(Query query) throws MinuteProjectException {
		CmisModel cmisModel = query.getQueries().getStatementModel().getModel()
				.getCmisModel();
		Session session = ConnectionUtils.getSession(cmisModel);
		if (session != null) {
			String q = QueryUtils.getFullQuerySample(query);
			try {
				return getOutputParams(session, q);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new MinuteProjectException("Query Not working "+query,"QUERY_NOT_WORKING");
			}
		}
		return null;
	}
	
	private static QueryParams getOutputParams(Session session, String query) throws SQLException {
		ItemIterable<QueryResult> results = session.query(query, false);
		return getQueryParams(results);

	}

	private static QueryParams getQueryParams(ItemIterable<QueryResult> results) throws SQLException {
		QueryParams queryParams = new QueryParams();
		queryParams.setQueryParams(getQueryParamsList(results));
		return queryParams;
	}

	private static List<QueryParam> getQueryParamsList(ItemIterable<QueryResult> results) throws SQLException {
		for (QueryResult result : results) {
        	return getQueryParamsList(result);
        }
		return new ArrayList<QueryParam>();
	}
	
	private static List<QueryParam> getQueryParamsList(QueryResult result) throws SQLException {
        List<QueryParam> list = new ArrayList<QueryParam>();
        
        for (PropertyData prop : result.getProperties()) {
        	QueryParam qp = new QueryParam();
        	logger.debug("> "+prop.getQueryName()+", "+prop.getFirstValue().getClass().getName());
//        	qp.setName(prop.getQueryName());
        	qp.setName (getNameFromCmis(prop.getQueryName()));
        	qp.setAlias(getNameFromCmis(prop.getQueryName()));
        	qp.setIsAliasSet(true);
        	qp.setType(getType(prop.getFirstValue().getClass().getName()));
        	list.add(qp);
        }
        return list;
	}

	private static String getNameFromCmis(String queryName) {
		
		String result =  StringUtils.replace(queryName, ":", "_");
		result = StringUtils.replace(result, "cmis_", "");
		result = FormatUtils.decamelCaseForSqlAliasing(result);
		return result;
	}

	private static String getType(String name) {
		if ("java.lang.String".equals(name)) {
			return "VARCHAR";//java.sql.Types.VARCHAR
		} 
		else if ("java.util.GregorianCalendar".equals(name)) {
			return "DATE";
		} 
		else if ("java.lang.Boolean".equals(name)) {
			return "BIT";
		} 
		else if ("java.lang.Date".equals(name)) {
			return "DATE";
		} else if ("java.math.BigDecimal".equals(name)) {
			return "DOUBLE";		
		} else if ("java.math.BigInteger".equals(name)) {
			return "BIGINT";
		}
		return null;
	}

}
