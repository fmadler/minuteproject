package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.constant.QuerySortOrder;

public class EntitySort<T> {

	private final T entity;
	private final QuerySortOrder order;
	
	public EntitySort(T entity, QuerySortOrder order) {
		super();
		this.entity = entity;
		this.order = order;
	}

	public T getEntity() {
		return entity;
	}

	public QuerySortOrder getOrder() {
		return order;
	}

	@Override
	public String toString() {
		return "EntitySort [entity=" + entity + ", QuerySortOrder=" + order + "]";
	}
}
