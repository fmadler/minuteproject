package net.sf.minuteProject.model.data.criteria.function;

import net.sf.minuteProject.model.data.criteria.DigitFunctionCriteria;
import net.sf.minuteProject.model.data.criteria.collector.WhatFieldCollector;

public class DoubleFunction extends DigitFunctionCriteria<Double>{

	public DoubleFunction(WhatFieldCollector wfc) {
		super(wfc);
	}

	@Override
	protected void setDefaultValue() {
		setValue(Double.valueOf(0));		
	}

}
