package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.collector.WhatFieldCollector;
import net.sf.minuteProject.model.data.criteria.face.IFunction;
import net.sf.minuteProject.model.data.criteria.function.DoubleFunction;
import net.sf.minuteProject.model.data.criteria.type.LongCriteria;

public abstract class DigitFunctionCriteria <T> extends FunctionCriteria<T> implements IFunction {

//	public static final String AVG 			= "AVG";
//	public static final String MAX	 		= "MAX";
//	public static final String MIN  		= "MIN";
//	public static final String COUNT 		= "COUNT";

	public DigitFunctionCriteria(WhatFieldCollector wfc) {
		super(wfc);
	}
	
	public WhatFieldCollector avg() {
		return addElement(AVG);
//		return DoubleFunction.setDefaultValue();
	}
	
	public WhatFieldCollector max () {
		return addElement(MAX);
	}
	
	public WhatFieldCollector min () {
		return addElement(MIN);
	}
	
	public WhatFieldCollector count () {
		return addElement(COUNT);
	}
	
	
}
