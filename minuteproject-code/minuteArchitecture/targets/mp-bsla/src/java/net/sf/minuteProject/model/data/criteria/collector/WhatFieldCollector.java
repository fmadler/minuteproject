package net.sf.minuteProject.model.data.criteria.collector;

import net.sf.minuteProject.model.data.criteria.order.OrderCriteria;

public class WhatFieldCollector extends WhatCollector{

	public static final String order = "ORDER";
	
	public void order (OrderCriteria order) {
		addElement(getField(), this.order, order.toString());
	}
	
}
