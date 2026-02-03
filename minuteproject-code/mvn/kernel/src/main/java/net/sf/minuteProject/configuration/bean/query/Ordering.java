package net.sf.minuteProject.configuration.bean.query;

import net.sf.minuteProject.configuration.bean.enumeration.Order;
import net.sf.minuteProject.configuration.bean.model.data.Column;

public class Ordering {

	private Order order;
	private Column column;
	private String columnName;
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Column getColumn() {
		return column;
	}
	public void setColumn(Column column) {
		this.column = column;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}
