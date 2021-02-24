package net.sf.minuteProject.architecture.filter.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderCriteria extends Criterion{

	private List<String> fields;
	
	public OrderCriteria(List<String> fields) {
		this.fields = fields;
	}
	
	@Override
	public String getExpression() {
		StringBuffer sb = new StringBuffer();
		List<String> fields = getFields();
		if (!fields.isEmpty()) {
			sb.append(" order by ");
			for (Iterator iter = fields.iterator(); iter.hasNext();) {
				String field = (String) iter.next();
				sb.append(field);
				if (iter.hasNext())
					sb.append(", ");
			}
			return sb.toString();
		}
		return "";
	}
	
	private List<String> getFields() {
		if (fields==null) 
			fields = new ArrayList<String>();
		return fields;
	}

}
