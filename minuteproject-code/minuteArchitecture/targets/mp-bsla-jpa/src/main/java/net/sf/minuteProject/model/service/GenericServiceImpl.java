package net.sf.minuteProject.model.service;

import net.sf.minuteProject.architecture.bsla.domain.AbstractDomainObject;
import net.sf.minuteProject.model.dao.GenericDao;
import net.sf.minuteProject.model.data.criteria.QueryData;

public class GenericServiceImpl <T extends AbstractDomainObject, D extends GenericDao<T>> implements GenericService<T>{

	D d;
	
	@Override
	public void find(QueryData<T> queryData) {	
		d.find(queryData);
	}
	
	@Override
	public void findWithoutCount(QueryData<T> queryData) {	
		d.findWithoutCount(queryData);
	}

}
