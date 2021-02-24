package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.List;

import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.cmis.CmisUtils;

public class CmisQueryModel extends QueryModel {


	public void setEntityModelSpecific(Query query, Direction dir,
			org.apache.ddlutils.model.Table table, Table entity) {
		initFieldAndRelationship(query, dir, table);
	}

	@Override
	public QueryParams getOutputParams(Query query)
			throws MinuteProjectException {
		return CmisUtils.getOutputParams(query);
	}
	
	private void initFieldAndRelationship(Query query, Direction dir,
			org.apache.ddlutils.model.Table table) {
		if (dir.equals(Direction.IN)) {
			List<QueryParam> list = query.getQueryParams(Direction.IN);
		}
	}
}
