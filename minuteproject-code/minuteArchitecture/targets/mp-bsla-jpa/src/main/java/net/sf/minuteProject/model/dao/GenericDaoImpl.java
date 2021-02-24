package net.sf.minuteProject.model.dao;

import java.util.List;

import net.sf.minuteProject.architecture.bsla.domain.AbstractDomainObject;
import net.sf.minuteProject.model.data.criteria.EntityCriteria;
import net.sf.minuteProject.model.data.criteria.EntitySort;
import net.sf.minuteProject.model.data.criteria.QueryData;
import net.sf.minuteProject.model.data.criteria.constant.EntityMatchType;
import net.sf.minuteProject.model.data.criteria.constant.OperandType;
import net.sf.minuteProject.model.data.criteria.constant.QuerySortOrder;
import static net.sf.minuteProject.model.utils.BuilderUtils.*;

public abstract class GenericDaoImpl <T extends AbstractDomainObject> implements GenericDao<T> {

	@Override
	public void findWithoutCount (QueryData<T> data) {
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
	}
	
	@Override
	public void find (QueryData<T> data) {
		findWithoutCount(data);
		T entityWhat = data.getEntityWhat();
		EntityCriteria<T> filter = data.getEntityCriteria();
		T criteriaMask = filter.getEntity();
		Integer max = data.getMax();
		List<T> results = data.getResult();
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
//	
//    protected String findQuery (T criteriaWhat, T criteriaMask, T orderMask, String what, EntityMatchType matchType, OperandType operandType, Boolean caseSensitivenessType, QuerySortOrder sortOrder) {
//        String queryWhere = findWhere (criteriaMask, false, isAll(matchType), operandType, caseSensitivenessType);
//		String queryOrder = findOrder (orderMask, sortOrder);
//	    return getHQuery(what, queryWhere, queryOrder);
//    }
    
    protected String getWhereEqualAnyWhereQueryChunk (T t, boolean isAndSet) {
		return getSearchEqualWhereQueryChunk (t, isAndSet, false);	
	}
	
    protected String getWhereEqualWhereQueryChunk (T t, boolean isAndSet) {
		return getSearchEqualWhereQueryChunk (t, isAndSet, true);
	}
    
    public List<T> searchPrototypeCategory(T t) {
        return searchPrototype (t, null);
     }  
    protected List<T> searchPrototype (T t, Integer maxResults) {
        return searchPrototype(t, null, null, maxResults);
    }
    
    protected List<T> searchPrototype (T t, T orderMask, QuerySortOrder sortOrder, Integer maxResults) {
        return searchPrototype(getSelectQuery (getWhereEqualWhereQueryChunk(t), orderMask, sortOrder), maxResults);
    }

	protected String getSelectQuery (String where, T orderMask, QuerySortOrder sortOrder) {
        return getSelectQuery (where, findOrder (orderMask, sortOrder));
    }
    
    protected String getSelectQuery (String where, String order) {
        StringBuffer query = new StringBuffer();
        query.append (getSelectFrom());//"SELECT t FROM T t ");
        return getHQuery(query.toString(), where, order);
    }
    
	protected String getWhereEqualWhereQueryChunk (T t) {
        return getWhereEqualWhereQueryChunk(t, false);
    }
	
    protected abstract String getSelectFrom() ;

    protected abstract boolean isAllNull (T t);
    
    protected abstract String getSearchEqualWhereQueryChunk(T t, boolean isAndSet, boolean b) ;

	protected abstract String findWhere(T criteriaMask, boolean b, boolean all,
			OperandType operandType, Boolean caseSensitivenessType) ;

	protected abstract String findOrder(T orderMask, QuerySortOrder sortOrder);

    protected abstract List<T> searchPrototype(String selectQuery, Integer maxResults) ;
    
    protected abstract T assignBlankToNull (T t);
    
}
