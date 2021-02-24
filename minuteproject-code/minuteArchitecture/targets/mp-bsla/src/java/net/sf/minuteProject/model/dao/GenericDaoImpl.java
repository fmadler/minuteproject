package net.sf.minuteProject.model.dao;

import java.util.List;

import net.sf.minuteProject.model.data.criteria.EntityCriteria;
import net.sf.minuteProject.model.data.criteria.EntitySort;
import net.sf.minuteProject.model.data.criteria.QueryData;
import net.sf.minuteProject.model.data.criteria.constant.EntityMatchType;
import net.sf.minuteProject.model.data.criteria.constant.OperandType;
import net.sf.minuteProject.model.data.criteria.constant.QuerySortOrder;

public abstract class GenericDaoImpl <T> implements GenericDao<T> {

	public void find (QueryData<T> data) {
		EntityCriteria<T> filter = data.getEntityCriteria();
		T entityWhat = data.getEntityWhat();
		T criteriaMask = filter.getEntity();
		Integer start = data.getStart();
		Integer max = data.getMax();
		EntitySort<T> entitySort = data.getEntitySort();
		QuerySortOrder sortOrder = entitySort.getOrder();
		T sortMask = entitySort.getEntity();	

		List<T> results = find(entityWhat, criteriaMask, sortMask, filter.getMatchType(), filter.getOperandType(), filter.getCaseSensitivenessType(), sortOrder, start, max);
		data.setResult(results);
		int size = results.size();
		if (size<max) 
			data.setTotalResultCount(Long.valueOf(size));
		else
			data.setTotalResultCount(count(entityWhat, criteriaMask, filter.getMatchType(), filter.getOperandType(), filter.getCaseSensitivenessType()));

	}
	
	protected abstract Long count(T entityWhat, T criteriaMask, EntityMatchType matchType,
				OperandType operandType, Boolean caseSensitivenessType) ;
	
	protected abstract List<T> find(T entityWhat, T criteriaMask, T sortMask,
			EntityMatchType matchType, OperandType operandType,
			Boolean caseSensitivenessType, QuerySortOrder sortOrder, Integer start,
			Integer max) ;
}

