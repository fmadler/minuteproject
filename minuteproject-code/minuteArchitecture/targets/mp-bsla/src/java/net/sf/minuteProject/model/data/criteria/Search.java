package net.sf.minuteProject.model.data.criteria;

public interface Search {

	// what , where, limit, order
	
	public String getQuery();
	
	public String getQueryStatement();
	
	public String getParameters();
	
}
