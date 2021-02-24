package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.collector.WhereFieldCollector;

public class ComparisonCriteria<T> extends EvaluationCriteria<T> {

	public static final String LT = "LT";
	public static final String LE = "LE";
	public static final String GT = "GT";
	public static final String GE = "GE";
	
	public ComparisonCriteria(WhereFieldCollector wfc) {
		super(wfc);
	}
	
	public WhereFieldCollector lt(T t) {
		// with template
		wfc.addElement(LT, t);
		return wfc;
	}
	
	public WhereFieldCollector le(T t) {
		wfc.addElement(LE, t);
		return wfc;
	}
	
	public WhereFieldCollector gt(T t) {
		wfc.addElement(GT, t);
		return wfc;
	}
	
	public WhereFieldCollector ge(T t) {
		wfc.addElement(GE, t);
		return wfc;
	}
	
}
