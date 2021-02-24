package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;

public class QueryPivot extends AbstractConfiguration{
	
	private Query query;
	private String pivot;
	private String columns;
	private List<Column> columnList;
	private List<QueryPivot> siblings;
	

	public String getPivot() {
		return pivot;
	}
	public Column getPivotColumn () {
		return ColumnUtils.getColumn(query.getOutputBean(), pivot);
	}
	public void setPivot(String pivot) {
		this.pivot = pivot;
	}
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
	
	public Query getQuery() {
		return query;
	}
	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}
	public void setSiblings(List<QueryPivot> siblings) {
		this.siblings = siblings;
	}
	public List<Column> getColumnList() {
		if (columnList==null) {
			columnList = ColumnUtils.getColumns(query.getOutputBean(), columns);
		}
		return columnList;
	}
	public List<QueryPivot> getSiblings() {
		
		if (siblings==null) {
			siblings = new ArrayList<QueryPivot>(query.getPivots());
			siblings.remove(this);
		}
		return siblings;
	}

}
