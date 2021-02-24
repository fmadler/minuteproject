package net.sf.minuteProject.model.data.criteria.holder;

import java.util.ArrayList;
import java.util.List;

public class WhatHolder <E>{

	String beanPath, field, function; 
	List<E> param;
	
	
	public WhatHolder(String beanPath, String field, String operator, E... param) {
		super();
		this.beanPath = beanPath;
		this.field = field;
		this.function = operator;
		for (E e : param) {
			addParam(e);
		}
	}
	
	public String getBeanPath() {
		return beanPath;
	}
	public void setBeanPath(String beanPath) {
		this.beanPath = beanPath;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOperator() {
		return function;
	}
	public void setOperator(String operator) {
		this.function = operator;
	}
	public List<E> getParam() {
		if (param==null)
			param = new ArrayList<E>();
		return param;
	}
	public void setParam(List<E> param) {
		this.param = param;
	}
	public void addParam(E param) {
		getParam().add(param);
	}
	
	public String toString() {
		return beanPath + " - " + field + " - " + function + " - " +param;
	}
}
