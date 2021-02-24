package net.sf.minuteProject.configuration.bean.model.statement;

import lombok.Data;

@Data
public class QueryPagination {
	
//	private int maxResult, offset;
	private Integer maxResultDefault;
	private Integer offsetDefault;
	private Query query;
	private String maxResultParam = "limit";
	private String offsetParam = "offset";
	
}
