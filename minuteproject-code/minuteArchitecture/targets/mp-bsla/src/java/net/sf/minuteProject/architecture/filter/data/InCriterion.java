package net.sf.minuteProject.architecture.filter.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InCriterion extends ClauseCriterion {

	private List<String> values;
	private String field;
	private boolean isQuoted;
	
	public InCriterion(String field, List<String> values, boolean isQuoted) {
		this.field = field;
		this.values = values;
		this.isQuoted = isQuoted;
	}
	
	@Override
	public String getExpression() {
		StringBuffer sb = new StringBuffer();
		List<String> values = getValues();
		if (!values.isEmpty()) {
			sb.append(" "+field+" in (");
			for (Iterator iter = values.iterator(); iter.hasNext();) {
				String value = (String) iter.next();
				if (!isQuoted)
					sb.append(value);
				else
					sb.append("'"+value+"'");
				if (iter.hasNext())
					sb.append(", ");
			}
			sb.append(")");
			return sb.toString();
		}
		return "";
	}
	
	private List<String> getValues() {
		if (values==null) 
			values = new ArrayList<String>();
		return values;
	}
}
