package net.sf.minuteProject.architecture.filter.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IsNullCriterion extends ClauseCriterion {

	private String field;
	private boolean isNull;
	
	public IsNullCriterion(String field, boolean isNull) {
		this.field = field;
		this.isNull = isNull;
	}
	
	@Override
	public String getExpression() {
		StringBuffer sb = new StringBuffer();
		sb.append(" "+field+" is ");
		if (!isNull)
			sb.append (" not ");
		sb.append(" null ");
		return sb.toString();
	}
	
}
