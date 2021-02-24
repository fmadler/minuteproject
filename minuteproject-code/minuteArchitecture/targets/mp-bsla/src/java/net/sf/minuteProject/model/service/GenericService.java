package net.sf.minuteProject.model.service;

import net.sf.minuteProject.model.data.criteria.QueryData;

public interface GenericService <T> {

	
	/**
	 * Find persistence data according to search criteria
	 * @param queryData
	 */
	public void find (QueryData<T> queryData);
	
}
