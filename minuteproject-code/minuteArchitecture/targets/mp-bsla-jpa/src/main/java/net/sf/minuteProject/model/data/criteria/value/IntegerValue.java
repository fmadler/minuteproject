package net.sf.minuteProject.model.data.criteria.value;

import net.sf.minuteProject.model.data.criteria.collector.WhatFieldCollector;

public class IntegerValue extends DigitReturnValue<Integer> {
//
//	public IntegerValue(WhatFieldCollector wfc) {
//		super(wfc);
//	}
//	
	public IntegerValue(Integer i) {
		setValue(i);
	}

	@Override
	protected void setDefaultValue() {
		setValue(getDefaultValue());
	}

	@Override
	protected Integer getDefaultValue() {
		return Integer.valueOf(0);
	}

	@Override
	protected DigitReturnValue<Integer> getDefault() {
		IntegerValue iv = new IntegerValue(getDefaultValue());
		return iv;
	}
	
}
