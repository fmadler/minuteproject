package net.sf.minuteProject.model.service;

import net.sf.minuteProject.architecture.bsla.domain.AbstractDomainObject;
import net.sf.minuteProject.model.data.criteria.QueryData;

public interface GenericService <T extends AbstractDomainObject> {

	
	/**
	 * Find persistence data according to search criteria
	 * @param queryData
	 */
	public void find (QueryData<T> queryData);
	
	/**
	 * Find persistence data according to search criteria
	 * without counting the number of results
	 * @param queryData
	 */
	public void findWithoutCount (QueryData<T> queryData);
	
}
