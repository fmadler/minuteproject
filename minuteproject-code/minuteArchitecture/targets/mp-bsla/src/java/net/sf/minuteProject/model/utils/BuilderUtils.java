package net.sf.minuteProject.model.utils;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.model.data.criteria.constant.EntityMatchType;
import net.sf.minuteProject.model.data.criteria.constant.OperandType;

public class BuilderUtils {

	public static String getOperand (OperandType operandType) {
	    if (OperandType.EQUALS.equals(operandType))
		   return "=";
	    if (OperandType.LIKE.equals(operandType))
		   return "like";
	    if (OperandType.CONTAINS.equals(operandType))
		   return "like";
	    if (OperandType.STARTWITH.equals(operandType))
		   return "like";
		return "=";
	}
	
	public static String getEvaluatorPrefix (OperandType operandType) {
	    if (OperandType.CONTAINS.equals(operandType))
		   return "%";
		return "";
	}
	
	public static String getEvaluatorSuffix (OperandType operandType) {
	    if (OperandType.LIKE.equals(operandType))
		   return "%";
	    if (OperandType.CONTAINS.equals(operandType))
		   return "%";
        if (OperandType.STARTWITH.equals(operandType))
           return "%";
		return "";
	}
	
	public static boolean isAll(EntityMatchType matchType) {
		return EntityMatchType.ALL.equals(matchType)?true:false;
	}
	
    //TODO put in upper class
    
	public static String getQueryWHERE_AND(boolean isWhereSet) {
		if (isWhereSet)
			return " AND ";
		return " WHERE ";
	}

	public static String getQueryCommaSet(boolean isWhereSet) {
		if (isWhereSet)
			return " , ";
		return " SET ";
	}

	public static String getQuerySelectComma(boolean isSelectSet) {
		if (isSelectSet)
			return " , ";
		return " select ";
	}

	public static String getQueryBLANK_AND(boolean isBlankSet) {
		if (isBlankSet)
			return " AND ";
		return " ";
	}

	public static String getQueryBLANK_COMMA(boolean isBlankSet) {
        if (isBlankSet)
           return " , ";
        return " ";
	}

	public static String getQueryComma(boolean isCommaSet) {
        return (isCommaSet) ? " , " : "";
	}

	public static String getQueryOR(boolean isOrSet) {
        return (isOrSet) ? " OR " : "";
	}

	public static String getQueryAND(boolean isAndSet) {
        return (isAndSet) ? " AND " : "";
	}

	public static String getHQuery (String what, String where) {
        String whereWord = getWhere (where);
        return what+whereWord+where;
    }

	public static String getHQuery (String what, String where, String order) {
        String orderbyWord = getOrder(order);
        return getHQuery(what, where)+orderbyWord+order;
    }
    
	public static String getWhere (String where) {
        return (StringUtils.isEmpty(where))?"":" WHERE ";
    }
	
	public static String getOrder (String order) {
        return (StringUtils.isEmpty(order))?"":" ORDER BY ";
    }
		

}
