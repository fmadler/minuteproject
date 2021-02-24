package net.sf.minuteProject.configuration.bean.model.statement;

public class QueryFilter extends QueryAdapter {

	private QueryParams queryParams;
	public enum AndWhere { AND, WHERE, NONE };
	private AndWhere connectWord = AndWhere.AND;
	private boolean omitFromSddLookupQuery = false; //omit-from-sdd-lookup-query;

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
		this.queryParams.setFilterName(getName());
		for (QueryParam queryParam : getQueryParams().getQueryParams()) {
			queryParam.setMandatory(false);
		}
	}

	public AndWhere getConnectWord() {
		return connectWord;
	}

	public void setConnectionWord(String connectionWord) {
		String upperCase = connectionWord.toUpperCase();
		if (upperCase.equals("AND")) {
			connectWord = AndWhere.AND;
		} else if (upperCase.equals("WHERE")) {
			connectWord = AndWhere.WHERE;
		} else {
			connectWord = AndWhere.NONE;
		}
	}

	public boolean isWhere() {
		return connectWord.equals(AndWhere.WHERE);
	}
	
	public boolean isNone() {
		return connectWord.equals(AndWhere.NONE);
	}

	public void setConnectWord(AndWhere andWhere) {
		this.connectWord = andWhere;
	}

	public boolean isOmitFromSddLookupQuery() {
		return omitFromSddLookupQuery;
	}

	public void setOmitFromSddLookupQuery(boolean omitFromSddLookupQuery) {
		this.omitFromSddLookupQuery = omitFromSddLookupQuery;
	}

	
}
