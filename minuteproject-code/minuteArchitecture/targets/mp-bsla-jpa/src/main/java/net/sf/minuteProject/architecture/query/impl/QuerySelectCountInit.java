package net.sf.minuteProject.architecture.query.impl;

import net.sf.minuteProject.architecture.query.QueryWhatInit;

public class QuerySelectCountInit implements QueryWhatInit {

	private String entity;
	
	public QuerySelectCountInit (String entity) {
		this.entity = entity;
	}
	
	public String getWhatInit(boolean isComma) {
       if (isComma)
           return " , ";
        return " select count("+entity+") as "+getWhatAlias()+" ";
	}

	@Override
	public boolean isToSeparateInit() {
		return true;
	}

	@Override
	public boolean isProjectionQuery() {
		return true;
	}

	@Override
	public String getWhatAlias() {
		return "count__";
	}

	@Override
	public String getWhatProperty() {
		return getWhatAlias();
	}

}
