package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.constant.QuerySortOrder;

public class EntitySort<T> {

	private T entity;
	private QuerySortOrder order;
	public EntitySort(T entity, QuerySortOrder order) {
		super();
		this.entity = entity;
		this.order = order;
	}
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public QuerySortOrder getOrder() {
		return order;
	}
	public void setOrder(QuerySortOrder order) {
		this.order = order;
	}
	
	@Override
	public String toString() {
		return "EntitySort [entity=" + entity + ", QuerySortOrder=" + order + "]";
	}
}
