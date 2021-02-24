package net.sf.minuteProject.architecture.query.impl;

import net.sf.minuteProject.architecture.query.QueryWhatInit;

public class QueryBlankInit implements QueryWhatInit{

	@Override
	public String getWhatInit(boolean isComma) {
	       if (isComma)
	           return " , ";
	        return " ";
	}

	@Override
	public boolean isProjectionQuery() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isToSeparateInit() {
		// TODO Auto-generated method stub
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
