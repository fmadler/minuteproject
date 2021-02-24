package net.sf.minuteProject.model.data.criteria.collector;

import java.util.Enumeration;
import java.util.Stack;

import net.sf.minuteProject.model.data.criteria.holder.WhereHolder;

public class WhereCollector <E> extends CriteriaCollector<E> {

	private Stack<WhereHolder<E>> s;
	private String entityPath, field;
	
	public WhereCollector() {
		super();
		s = new Stack<WhereHolder<E>>();
	}
	
	public WhereCollector(String entityPath) {
		super();
		s = new Stack<WhereHolder<E>>();
		this.entityPath = entityPath;
	}

	public void addElement (String operator, E... param){
		addElement (field, operator, param);
	}
	
	public void addElement (String field, String operator, E... param){
		addElement (entityPath, field, operator, param);
	}
	
	private void addElement (String beanPath, String field, String operator, E... param){
		WhereHolder<E> wh = new WhereHolder<E>(beanPath, field, operator, param);
		s.add(wh);
	}
	
	public Enumeration<WhereHolder<E>> getElements () {
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
