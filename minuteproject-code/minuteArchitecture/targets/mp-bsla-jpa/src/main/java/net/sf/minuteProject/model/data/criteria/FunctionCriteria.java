package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.collector.WhatFieldCollector;

public abstract class FunctionCriteria<T> implements Criteria{

	public static final String MASK 		= "MASK";
	
	private T t;
	protected WhatFieldCollector wfc;
	
	public FunctionCriteria(WhatFieldCollector wfc) {
		this.wfc = wfc;
	}
	
	public WhatFieldCollector mask () {
		return addElement(MASK);
	}
	
	public T value() {
		return t;
	}
	
	protected void setValue (T t) {
		this.t = t;
	}
	
	abstract protected void setDefaultValue () ;
	
	protected final WhatFieldCollector addElement (String function, T... t) {
		wfc.addElement(function, t);
		return wfc;
	}
	
}
