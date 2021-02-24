package net.sf.minuteProject.model.dao;

import net.sf.minuteProject.model.data.criteria.QueryData;
import net.sf.minuteProject.model.data.criteria.constant.QuerySortOrder;

public interface GenericDao <T> {

	
	/**
	 * Find persistence data according to search criteria
	 * @param queryData
	 */
	public void find (QueryData<T> queryData);
	
	public void getList(T mask, T sortMask, QuerySortOrder order);
	
}
