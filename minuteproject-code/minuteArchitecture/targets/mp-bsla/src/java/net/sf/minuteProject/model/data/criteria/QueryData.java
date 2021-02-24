package net.sf.minuteProject.model.data.criteria;

import java.util.List;

public class QueryData <T> {

	private List<T> result;
	private Long totalResultCount;
	
	private Integer start;
	private Integer max;
	private EntitySort<T> entitySort;
	private EntityCriteria<T> entityCriteria;
	private T entityWhat;
	
	public QueryData(Integer start, Integer max, EntitySort<T> entitySort, EntityCriteria<T> entityCriteria, T entityWhat) {	
		this(start, max, entitySort, entityCriteria);
		this.entityWhat = entityWhat;
	}

	public QueryData(Integer start, Integer max, EntitySort<T> entitySort, EntityCriteria<T> entityCriteria) {	
		this.start = start;
		this.max = max;
		this.entitySort = entitySort;
		this.entityCriteria = entityCriteria;
	}
	
	public List<T> getResult() {
		return result;
	}

	public Long getTotalResultCount() {
		return totalResultCount;
	}

	public int getStart() {
		return start;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public void setTotalResultCount(Long totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public EntitySort<T> getEntitySort() {
		return entitySort;
	}

	public void setEntitySort(EntitySort<T> entitySort) {
		this.entitySort = entitySort;
	}

	public EntityCriteria<T> getEntityCriteria() {
		return entityCriteria;
	}

	public void setEntityCriteria(EntityCriteria<T> entityCriteria) {
		this.entityCriteria = entityCriteria;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public T getEntityWhat() {
		return entityWhat;
	}
	
	@Override
	public String toString() {
		return "QueryData [start=" + start + ", end=" + max + ", entitySort=" + entitySort + ", entityCriteria="
				+ entityCriteria + ", entityWhat= "+entityWhat+"]";
	}
	
}
