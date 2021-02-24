package net.sf.minuteProject.model.dao;

import java.util.List;

import net.sf.minuteProject.architecture.bsla.domain.AbstractDomainObject;
import net.sf.minuteProject.model.data.criteria.QueryData;
import net.sf.minuteProject.model.data.criteria.constant.QuerySortOrder;

public interface GenericDao <T extends AbstractDomainObject> {

    /**
    * Saves a T entity 
    * @param T t
    */
   public void save(T t);
   
   /**
    * Deletes a T entity 
    * @param T t
    */
   public void delete(T t) ;

    /**
     * Inserts a T entity 
     * @param T t
     */
    public void insert (T t) ;
 
    /**
     * Inserts a list of T entity 
     * @param List<T> ts
     */
    public void insert(List<T> list) ;
        
    /**
     * Updates a T entity 
     * @param T t
     */
    public T update(T t) ;
	/**
	 * Find persistence data using QueryData
	 * @param queryData
	 */
	public void find (QueryData<T> queryData);
	/**
	 * Find persistence data using QueryData without counting the total matches
	 * @param queryData
	 */
	public void findWithoutCount (QueryData<T> queryData);
	
	public List<T> list(T mask, T sortMask, QuerySortOrder order);
	
}
