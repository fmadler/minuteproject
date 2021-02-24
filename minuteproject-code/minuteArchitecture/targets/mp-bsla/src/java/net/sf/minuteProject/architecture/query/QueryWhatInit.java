package net.sf.minuteProject.architecture.query;

public interface QueryWhatInit {

	public String getWhatInit(boolean isComma);
	
	public boolean isToSeparateInit();
	
	public boolean isProjectionQuery();
	
	public String getWhatAlias();
	
	public String getWhatProperty();
	
}
