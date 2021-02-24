package net.sf.minuteProject.architecture.utils;

import java.util.Hashtable;
import java.util.Map;

import net.sf.minuteProject.architecture.query.QueryWhatInit;

public class DataAccessObjectUtils {

	public static Map<Integer, String> getBeanPath(QueryWhatInit queryWhatInit) {
		Map<Integer, String> map = new Hashtable<Integer, String>();
		if (queryWhatInit.isProjectionQuery())
			map.put(map.size() , queryWhatInit.getWhatProperty());
		return map;
	}
	
	public static String getWhatStart(QueryWhatInit queryWhatInit, String what) {
		String whatStart = queryWhatInit.getWhatInit(false);
		if (queryWhatInit.isToSeparateInit() && isSubstantialWhat(what))
			whatStart = whatStart+" , ";
		return whatStart + what;
	}

    private static boolean isSubstantialWhat(String what) {
    	if (what==null)
    		return false;
    	return (what.trim()=="")?false:true;
	}

//	public static String getQueryMain (String what, String how, String whereHow, String where, String whereWord) {
//		String whereConcat = " AND ";
//		if (where!=null && !where.equals("") && whereHow!=null && !whereHow.equals(""))
//			return what+" FROM "+how + whereWord +whereHow+ whereConcat +where;
//		else 
//			return what+" FROM "+how;
//	}
//	
//	public static String getQueryMain (String whatStart, String what, String how, String whereHow, String where, boolean isProjectionQuery) {
//		String whereWord = (isProjectionQuery)?" HAVING ":" WHERE ";
//		String mainQuery = getQueryMain (whatStart, how, whereHow, where, whereWord);
//		return (isProjectionQuery)?mainQuery+" GROUP BY "+what:mainQuery;
//	}
	
	public static String getQueryMain (String whatStart, String what, String how, String whereHow, String where, boolean isProjectionQuery) {
//		String whereWord = (isProjectionQuery)?" HAVING ":" WHERE ";
//		String mainQuery = getQueryMainWhere (whatStart, how, whereHow, where);
		return (isProjectionQuery && isSubstantialWhat(what))?getQueryMainHaving (whatStart, what, how, whereHow, where):getQueryMainWhere (whatStart, how, whereHow, where);
	}
	
	public static String getQueryMainWhere (String what, String how, String whereHow, String where) {
		String whereConcat = " AND ";
		String whereWord = " WHERE ";
		if (where!=null && !where.equals(""))
			if (whereHow!=null && !whereHow.equals(""))
				return what+" FROM "+how + whereWord +whereHow+ whereConcat +where;
			else
				return what+" FROM "+how + whereWord +where;
		else 
			return what+" FROM "+how;
	}
	
	public static String getQueryMainHaving (String whatStart, String what, String how, String whereHow, String where) {
		String whereConcat = " AND ";
		String whereWord = " WHERE ";
		if (where!=null && !where.equals(""))
			if (whereHow!=null && !whereHow.equals(""))
				return whatStart+" FROM "+how + whereWord +whereHow+ whereConcat +where +" GROUP BY "+what ;
			else
				return whatStart+" FROM "+how + whereWord +where +" GROUP BY "+what ;
		else 
			return whatStart+" FROM "+how;
	}
}
