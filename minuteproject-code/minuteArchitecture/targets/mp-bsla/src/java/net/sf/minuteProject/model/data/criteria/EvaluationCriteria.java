package net.sf.minuteProject.model.data.criteria;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.model.data.criteria.collector.CriteriaCollector;
import net.sf.minuteProject.model.data.criteria.collector.WhereCollector;
import net.sf.minuteProject.model.data.criteria.collector.WhereFieldCollector;

public class EvaluationCriteria <T> implements Criteria {

	public static final String EQ 			= "EQ";
	public static final String IN	 		= "IN";
	public static final String ISNOTNULL  	= "ISNOTNULL";
	public static final String ISNULL 		= "ISNULL";
	
//	private T t;
//	private List<T> in;
//	private Boolean isNull;
//	private Boolean isNotNull;
	protected WhereFieldCollector wfc;
	
	public EvaluationCriteria(WhereFieldCollector wfc) {
		this.wfc = wfc;
	}
	
	public WhereFieldCollector eq(T t) {
		wfc.addElement(EQ, t);
		return wfc;
	}
	
	public WhereFieldCollector in (T... t) {
		wfc.addElement(IN, t);
//		in = new ArrayList<T>();
//		for (int i = 0; i < t.length; i++) {
//			in.add(t[i]);
//		}
		return wfc;
	}
	
	public WhereFieldCollector isNull () {
		wfc.addElement(ISNULL);
		return wfc;
	}
	
	public WhereFieldCollector isNotNull () {
		wfc.addElement(ISNOTNULL);
		return wfc;
	}
	
//
//	public void in (SubQuery list<Pk>) { // subquery 4 businessInjection (duplicate)...
//		
//	}
}
