package net.sf.minuteProject.architecture.bsla.dao.face;

import java.util.List;

import net.sf.minuteProject.architecture.bsla.bean.criteria.PaginationCriteria;

public interface GenericDao <T> extends DataAccessObject {

    /**
     * Inserts a T entity 
     * @param T t
     */
    public void insert (T t) ;
 
    /**
     * Inserts a list of T entity 
     * @param List<T> ts
     */
    public void insert(List<T> ts) ;
        
    /**
     * Updates a T entity 
     * @param T t
     */
    public T update(T t) ;

	/**
     * Updates a T entity with only the attributes set into T.
	 * The primary keys are to be set for this method to operate.
	 * This is a performance friendly feature, which remove the udibiquous full load and full update when an
	 * update is to be done
     * Remark: The primary keys cannot be update by this methods, nor are the attributes that must be set to null.
     * @param T t
   */
    public int updateNotNullOnly(T t) ;
	 
	public int updateNotNullOnlyPrototype(T t, T prototypeCriteria);
	
     /**
     * Saves a T entity 
     * @param T t
     */
    public void saveT(T t);
    
    /**
     * Deletes a T entity 
     * @param T t
     */
    public void deleteT(T t) ;
 
    /**
     * Loads the T entity which is related to an instance of
     * T
     * @param Long id
     * @return T The T entity
     
    public T loadT(Long id);
*/
    /**
     * Loads the T entity which is related to an instance of 
     * T
     * @param T
     * @return T The T entity
     */
    public T loadByPk(T t);    

    /**
     * Loads a list of T entity 
     * @param List<java.lang.String> propertys
     * @return List<T> The T entity
     */
    public List<T> loadTListBy (List<T> ts);
    
    /**
     * Loads a list of T entity 
     * @param List<java.lang.String> propertys
     * @return List<T> The T entity
     */
    public List<T> loadTListByPk(List<T> t);
    
    /**
     * Loads the T entity which is related to an instance of
     * T and its dependent one to many objects
     * @param T t
     * @return T The T entity
     */
    public T loadFullFirstLevelByPk(T t);
    
    /**
     * Loads the T entity which is related to an instance of
     * T
     * @param T t
     * @return T The T entity
     */
    public T loadFullFirstLevel(T t);    
    
    
    /**
     * Loads the T entity which is related to an instance of
     * T and its dependent objects one to many
     * @param Long id
     * @return T The T entity
     */
    public T loadFullT(Long id) ;

    /**
     * Searches a list of T entity based on a T containing T matching criteria
     * @param T t
     * @return List<T>
     */
    public List<T> searchPrototypeT(T t) ;
    
    /**
     * Searches a list of T entity based on a list of T containing T matching criteria
     * @param List<T> ts
     * @return List<T>
     */
    public List<T> searchPrototypeT(List<T> ts) ;    
    	
	/**
     * Searches a list of T entity 
     * @param T t
     * @return List
     */
    public List<T> searchPrototypeT(T tPositive, T tNegative) ;
        
    /**
     * Load a paginated list of T entity dependent of pagination criteria
     * @param PaginationCriteria paginationCriteria
     * @return List
     */
    public List<T> loadPaginated (T t, PaginationCriteria paginationCriteria) ;

}
