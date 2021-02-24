package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.collector.WhereFieldCollector;

public class RangeCriteria <T> extends ComparisonCriteria<T> {

	public static final String BETWEEN = "BETWEEN";
	
	public RangeCriteria (WhereFieldCollector wfc) {
		super(wfc);
	}

	public WhereFieldCollector between (T t1, T t2) {
		wfc.addElement(BETWEEN, t1, t2);
		return wfc;
	}
	
}
