package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class QueryParams extends AbstractConfiguration {

	private List<QueryParam> queryParams; //real stuff for query
	private List<QueryParam> flatQueryParams; //real stuff for converted from reference
	//stripped from self reference refid (considered as duplicate)
	private List<QueryParam> uniqueQueryParams; //stuff to transfer
	//stripped from self reference refid (considered as duplicate)
	//stripped of implicit and context queryParam
	private List<QueryParam> uniqueQueryParamsNotContextual; //stuff to display as user input
	
	//uniqueQueryParamsNotContextual is enriched with context + implicit param to get uniqueQueryParam
	//uniqueQueryParam is the input to the system
	//uniqueQueryParam is enriched with duplicate to get the queryParams
	private boolean isFilter=false;
	private String filterName; 
	
	private Query query;

	public boolean isEmpty () {
		return getQueryParams().isEmpty();
	}
	
	/**
	 * query params stripped from the refid (considered as duplicate entry although needed for the query)
	 * @return
	 * 
	 */
	public List<QueryParam> getUniqueQueryParams() {
		if (uniqueQueryParams == null) {
			uniqueQueryParams = new ArrayList<>();
			for (QueryParam queryParam : getQueryParams()) {
				if (StringUtils.isEmpty(queryParam.getRefid()) || StringUtils.isEmpty(queryParam.getRefname())) {
					uniqueQueryParams.add(queryParam);
				} 
			}
		}
		return uniqueQueryParams;
	}
	
	public void resetFlatQueryParams() {
		flatQueryParams=null;
	}
	
	public List<QueryParam> getFlatQueryParams(boolean isMandatoryDefault) {
		if (flatQueryParams == null) {
			flatQueryParams = new ArrayList<>();
			for (QueryParam queryParam : getQueryParams()) {
				if (!queryParam.isOutputParam()) {
					boolean isMandatory = (queryParam.isMandatory())?true:isMandatoryDefault;
					String refidParam = queryParam.getRefid();
					if (!StringUtils.isEmpty(refidParam)) {
						QueryParam instance = getReferenceIdQueryParam(refidParam);
						if (instance!=null) {
							copy (instance, queryParam, isMandatory);
							flatQueryParams.add(queryParam);
						}
					} else if (!StringUtils.isEmpty(queryParam.getRefname())) {
						QueryParam instance = getReferenceNameQueryParam(queryParam.getRefname());
						if (instance!=null) {
							copy (instance, queryParam, isMandatory);
							flatQueryParams.add(queryParam);
						}
					} else {
						queryParam.setMandatory(isMandatory);
						flatQueryParams.add(queryParam);
					}
				} else {
					QueryParam instance = queryParam;//TODO check how to flatten output param
					flatQueryParams.add(instance);
				}
			}
		}
		return flatQueryParams;
	}
	
	private void copy(QueryParam instance, QueryParam queryParam,boolean isMandatory) {
		queryParam.setName(instance.getName());
		queryParam.setType(instance.getType());
		queryParam.setProperties(instance.getProperties());
		queryParam.setSample(instance.getSample());
		queryParam.setSize(instance.getSize());
		queryParam.setScale(instance.getScale());
		queryParam.setStereotype(instance.getStereotype());
		queryParam.setDefaultValue(instance.getDefaultValue());
		queryParam.setHasBeenDuplicated(true);
		queryParam.setMandatory(isMandatory);
		if (instance.getQueryParamLink()!=null) {
			queryParam.setQueryParamLink(instance.getQueryParamLink());
		}
	}

	public List<QueryParam> getQueryParams() {
		if (queryParams == null) {
			if (!StringUtils.isEmpty(refid)) {
				queryParams = getReferenceQueryParams(refid);
			}
			if (queryParams == null)
				queryParams = new ArrayList<QueryParam>();
		}
		return queryParams;
	}

	private QueryParam getReferenceIdQueryParam(String refid) {
		if (query != null) {
			//search first in its own query for query-param id
			for (QueryParam q : getQueryParams()) {
				if (q.getId()!=null && q.getId().equals(refid)) {
					return q;
				}
			}
			//TODO search in all queries for id
		}
		return null;
	}
	
	private QueryParam getReferenceNameQueryParam(String refName) {
		if (query != null) {
			//search first in its own query for query-param id
			for (QueryParam q : getQueryParams()) {
				if (q.getName()!=null && q.getName().equals(refName)) {
					return q;
				}
			}
			//TODO search in all queries for id
		}
		return null;
	}
	
	private List<QueryParam> getReferenceQueryParams(String refname) {
		if (query != null) {
			Queries queries = query.getQueries();
			if (queries != null)
				for (Query q : queries.getQueries()) {
					if (refid.equals(q.getId())) {
						return q.getQueryParams().getQueryParams();//copy(q);
					}
				}
		}
		return null;
	}

	public boolean hasOutputParam() {
		if (queryParams!=null) {
			for (QueryParam queryParam : queryParams) {
				if (queryParam.isOutputParam()) {
					return true;
				}
			}
		}
		return false;
	}

	public void setQueryParams(List<QueryParam> queryParams) {
		this.queryParams = queryParams;
	}

	public void addQueryParam(QueryParam queryParam) {
		queryParam.setQueryParams(this);
		getQueryParams().add(queryParam);
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Query getQuery() {
		return query;
	}
	
	public boolean isFilter() {
		return isFilter;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
}
