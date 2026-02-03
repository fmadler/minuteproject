package net.sf.minuteProject.configuration.bean.model.statement;

import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.exception.MinuteProjectException;

public abstract class QueryModel {

	public abstract void setEntityModelSpecific(Query query, Direction dir,
			org.apache.ddlutils.model.Table table, Table entity);
	
	public abstract QueryParams getOutputParams(Query query) throws MinuteProjectException;
}
