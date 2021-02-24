package net.sf.minuteProject.model.data.criteria.collector;

import java.util.Enumeration;
import java.util.Stack;

import net.sf.minuteProject.model.data.criteria.holder.WhatHolder;

public class WhatCollector <E> extends CriteriaCollector<E> {

	private Stack<WhatHolder<E>> s;
	private String entityPath, field;
	
	public WhatCollector() {
		super();
		s = new Stack<WhatHolder<E>>();
	}
	
	public WhatCollector(String entityPath) {
		super();
		s = new Stack<WhatHolder<E>>();
		this.entityPath = entityPath;
	}

	public void addElement (String operator, E... param){
		addElement (field, operator, param);
	}
	
	public void addElement (String field, String operator, E... param){
		addElement (entityPath, field, operator, param);
	}
	
	private void addElement (String beanPath, String field, String operator, E... param){
		WhatHolder<E> wh = new WhatHolder<E>(beanPath, field, operator, param);
		s.add(wh);
	}
	
	public Enumeration<WhatHolder<E>> getElements () {
		return s.elements();
//		Enumeration<WhereHolder<E>> e = s.elements();
//		return s.pop();
	}

	public String getEntityPath() {
		return entityPath;
	}

	public void setEntityPath(String entityPath) {
		this.entityPath = entityPath;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}
