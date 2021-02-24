package net.sf.minuteProject.configuration.bean.model.statement;

public class QueryChunkValue extends QueryAdapter{

	private boolean isDefault;
	private String params;
	private QueryChunk queryChunk;

	public boolean isDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public void setQueryChunk(QueryChunk queryChunk) {
		this.queryChunk = queryChunk;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
}
