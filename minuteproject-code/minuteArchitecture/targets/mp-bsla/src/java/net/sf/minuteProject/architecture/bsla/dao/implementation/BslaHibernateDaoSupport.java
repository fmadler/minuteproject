package net.sf.minuteProject.architecture.bsla.dao.implementation;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BslaHibernateDaoSupport extends HibernateDaoSupport {

	protected Query getQuery (String queryString, Integer nbOfResult) {
	   Query query = getSession().createQuery(queryString);
	   if (nbOfResult!=null)
	      query.setMaxResults(nbOfResult);
	   return query;		
	}

	protected SQLQuery getSQLQuery (String queryString, Integer nbOfResult) {
		SQLQuery query = getSession().createSQLQuery(queryString);
	   if (nbOfResult!=null)
	      query.setMaxResults(nbOfResult);
	   return query;		
	}
	    
	protected String getQueryWHERE_AND(boolean isWhereSet) {
		if (isWhereSet)
			return " AND ";
		return " WHERE ";
	}

	protected String getQueryCommaSet(boolean isWhereSet) {
		if (isWhereSet)
			return " , ";
		return " SET ";
	}

	protected static String getQuerySelectComma(boolean isSelectSet) {
		if (isSelectSet)
			return " , ";
		return " select ";
	}

	protected static String getQueryBLANK_AND(boolean isBlankSet) {
		if (isBlankSet)
			return " AND ";
		return " ";
	}

	protected static String getQueryBLANK_COMMA(boolean isBlankSet) {
		if (isBlankSet)
			return " , ";
		return " ";
	}

	protected String getQueryComma(boolean isCommaSet) {
		return (isCommaSet) ? " , " : "";
	}

	protected String getQueryOR(boolean isOrSet) {
		return (isOrSet) ? " OR " : "";
	}

	protected String getQueryAND(boolean isAndSet) {
		return (isAndSet) ? " AND " : "";
	}

	protected String getHQuery (String what, String where) {
        String whereWord = getWhere (where);
	    return what+whereWord+where;
    }
    
    protected String getWhere (String where) {
        return (where==null || where.trim().equals("") || where.trim()==null)?"":" WHERE ";
    }         
    protected String getFormattedDate (Timestamp time) {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       return simpleDateFormat.format(time);
    }
    
    protected String getFormattedDate (Date date) {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       return simpleDateFormat.format(date);
    }
    
    protected String getFormattedDateTimeStamp (Timestamp time) {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
       return simpleDateFormat.format(time);
    }
    
    protected String getFormattedTimeStamp (Timestamp time) {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
       return simpleDateFormat.format(time);
    }
    
    
}
