package net.sf.minuteProject.architecture.holder.element;

public class SimpleValidationError extends AbstractValidationError {

	private String errorField;
	
	public SimpleValidationError(String errorString) {
		super(errorString, null);
	}
	
	public SimpleValidationError(String errorString, String errorField) {
		super(errorString, null);
		this.errorField = errorField;
		
	}
	
	@Override
	public String getMessage() {
		return errorString;
	}

	public String getErrorField() {
		return errorField;
	}

	public String getPath() {
		return "";
	}
	
	

}
