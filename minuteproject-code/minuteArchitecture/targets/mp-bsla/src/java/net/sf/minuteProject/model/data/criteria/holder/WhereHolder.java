package net.sf.minuteProject.model.data.criteria.holder;

import java.util.ArrayList;
import java.util.List;

public class WhereHolder <E>{

	String beanPath, field, operator; 
	List<E> param;
	
	
	public WhereHolder(String beanPath, String field, String operator, E... param) {
		super();
		this.beanPath = beanPath;
		this.field = field;
		this.operator = operator;
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
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
		return beanPath + " - " + field + " - " + operator + " - " +param;
	}
}
