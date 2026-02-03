package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.Entity;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.sql.QueryUtils;

public class SqlQueryModel extends QueryModel {


	public void setEntityModelSpecific(Query query, Direction dir,
			org.apache.ddlutils.model.Table table, Table entity) {
		Database database = query.getQueries().getStatementModel().getModel().getDataModel().getDatabase();
		initFieldAndRelationship(query, dir, database, table);
		entity.setDatabase(database);
		entity.setScope(query.getQueryScope());
	}
	

	private void initFieldAndRelationship(Query query, Direction dir, Database database,
			org.apache.ddlutils.model.Table table) {
		if (dir.equals(Direction.IN)) {
			List<QueryParam> list = query.getQueryParams(Direction.IN);
			for (QueryParam queryParam : list) {
				Entity.assignForeignKey(database, table,
						queryParam.getLinkField());
			}
		}
	}


	@Override
	public QueryParams getOutputParams(Query query) throws MinuteProjectException {
		return QueryUtils.getOutputParams(query);
	}
}
