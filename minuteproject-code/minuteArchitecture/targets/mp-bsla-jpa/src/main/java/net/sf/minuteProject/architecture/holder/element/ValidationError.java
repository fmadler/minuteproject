package net.sf.minuteProject.architecture.holder.element;

public interface ValidationError extends InformationMessage {
	
	public String getErrorString ();
	
	public String getErrorField ();
	
	public Object getErrorObject();
	
	public String getPath();
	
}
