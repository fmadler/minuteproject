package net.sf.minuteProject.model.data.criteria.collector;

import java.util.List;

import net.sf.minuteProject.model.data.criteria.Criteria;

public class WhereFieldCollector extends WhereCollector{

//	private String field;
//
//	public String getField() {
//		return field;
//	}
//
//	public void setField(String field) {
//		this.field = field;
//	}
//	
	public WhereFieldCollector and (WhereFieldCollector wfc) {
		wfc.addElement("AND");
		return wfc;
	}
	
	public WhereFieldCollector or (WhereFieldCollector wfc) {
		wfc.addElement("OR");
		return wfc;
	}
}
