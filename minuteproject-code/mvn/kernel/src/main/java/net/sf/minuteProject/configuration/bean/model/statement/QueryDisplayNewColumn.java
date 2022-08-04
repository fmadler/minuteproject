package net.sf.minuteProject.configuration.bean.model.statement;

public class QueryDisplayNewColumn {
	
	private String id, name, type, classFromColumn, styleColorFromColumn;

	private QueryDisplay queryDisplay;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassFromColumn() {
		return classFromColumn;
	}

	public void setClassFromColumn(String classFromColumn) {
		this.classFromColumn = classFromColumn;
	}

	public String getStyleColorFromColumn() {
		return styleColorFromColumn;
	}

	public void setStyleColorFromColumn(String styleColorFromColumn) {
		this.styleColorFromColumn = styleColorFromColumn;
	}

	public QueryDisplay getQueryDisplay() {
		return queryDisplay;
	}

	public void setQueryDisplay(QueryDisplay queryDisplay) {
		this.queryDisplay = queryDisplay;
	}
	
	

}
