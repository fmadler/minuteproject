package net.sf.minuteProject.model.data.criteria;

import java.util.List;

import net.sf.minuteProject.architecture.bsla.domain.AbstractDomainObject;

/**
 * @author florianadler
 *
 * @param <T>
 */
public class QueryData <T extends AbstractDomainObject> {

	private List<T> result;
	private Long totalResultCount;
	
	private final Integer start;
	private final Integer max;
	private final EntitySort<T> entitySort;
	private final EntityCriteria<T> entityCriteria;
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
	
	/**
	 * It might be unnecessary to count, when making consecutive search
	 * with the same filtering criterias
	 * needsNewCount determines the necessity to search compare to another
	 * QueryData.
	 * The client use this method to choose between find or findWithoutCount methods
	 * @param queryData
	 * @return
	 */
	public Boolean needsNewCount(QueryData<T> queryData) {
		// always return true
		// except when the where or what criteria (sort criteria to append to what) have changed!
		if (queryData==null 
			|| queryData.getEntityCriteria()==null
			|| queryData.getEntityCriteria().getEntity()==null
			|| this.getEntityCriteria()==null
//			|| (this.getEntityWhat()==null && this.entityWhat==null)
			)
			return true;
		return !areEntityCriteriaSimilar(queryData);
	}
	
	private Boolean areEntityCriteriaSimilar(QueryData<T> queryData) {
		return queryData.getEntityCriteria().getEntity().equalsMask(this.getEntityCriteria().getEntity())
			&& ((queryData.getEntityWhat()==null && this.entityWhat==null) ||
			queryData.getEntityWhat().equalsMask(this.entityWhat));
/*		
		if (   queryData.getEntityCriteria().getEntity().equals(this.getEntityCriteria().getEntity())
				&& ((queryData.getEntityWhat()==null && this.entityWhat==null)
						|| queryData.getEntityWhat().equals(this.entityWhat)))
			return false;
		return true;
			*/
		
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

	public EntitySort<T> getEntitySort() {
		return entitySort;
	}

	public EntityCriteria<T> getEntityCriteria() {
		return entityCriteria;
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
