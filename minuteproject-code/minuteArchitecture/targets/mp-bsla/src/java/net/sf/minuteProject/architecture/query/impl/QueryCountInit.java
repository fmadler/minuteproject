package net.sf.minuteProject.architecture.query.impl;

public class QueryCountInit extends QuerySelectCountInit{

	public QueryCountInit(String entity) {
		super(entity);
	}

	@Override
	public boolean isToSeparateInit() {
		return false;
	}
//
//	@Override
//	public boolean isProjectionQuery() {
//		return true;
//	}
}
