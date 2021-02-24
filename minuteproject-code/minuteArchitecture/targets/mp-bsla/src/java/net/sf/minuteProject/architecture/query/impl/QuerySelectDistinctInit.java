package net.sf.minuteProject.architecture.query.impl;

import net.sf.minuteProject.architecture.query.QueryWhatInit;

public class QuerySelectDistinctInit implements QueryWhatInit {

	public String getWhatInit(boolean isComma) {
       if (isComma)
           return " , ";
        return " select distinct ";
	}
	
	@Override
	public boolean isToSeparateInit() {
		return false;
	}
	
	public boolean isProjectionQuery() {
		return false;
	}

	@Override
	public String getWhatAlias() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWhatProperty() {
		// TODO Auto-generated method stub
		return null;
	}

}
