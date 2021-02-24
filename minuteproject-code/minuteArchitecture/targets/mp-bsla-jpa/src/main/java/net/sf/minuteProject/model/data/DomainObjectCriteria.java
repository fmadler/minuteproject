package net.sf.minuteProject.model.data;

import net.sf.minuteProject.model.data.criteria.collector.CriteriaCollector;

public class DomainObjectCriteria <E> {

	private CriteriaCollector<E> criteriaCollector;
	private E e;
	
	public DomainObjectCriteria(E e) {
		super();
		this.e = e;
		criteriaCollector = new CriteriaCollector<E>();
	}
	
//	public void add(E e) {
//		criteriaCollector.push(e);
//	}
//	
//	public E pop() {
//		return criteriaCollector.pop();
//	}
//	
//	public String popToString() {
//		E e = pop();
//		return (e!=null)?e.toString():null;
//	}
//	
//	public E getCollector () {
//		return e;
//	}
//	
}
