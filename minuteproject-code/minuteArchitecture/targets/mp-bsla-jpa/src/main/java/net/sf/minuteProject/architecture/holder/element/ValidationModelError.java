package net.sf.minuteProject.architecture.holder.element;

public class ValidationModelError extends AbstractValidationError{
	public static final String CANNOT_VALIDATE = "CANNOT_VALIDATE";
	public static final String LENGTH_TOO_HIGH = "LENGTH_TOO_HIGH";
	public static final String ABSENT = "ABSENT";
	public static final String CONCURRENT_UPDATE = "CONCURRENT_UPDATE";
	public static final String LOCAL_REF_ID_ALREADY_MAPPED = "LOCAL_REF_ID_ALREADY_MAPPED";
	
	public ValidationModelError() {
		super();
	}
	public ValidationModelError(String errorString, Object errorObject, Object errorValue, Object acceptedValue) {
		super(errorString, errorObject, errorValue, acceptedValue);
	}
	public ValidationModelError(String errorString, Object errorObject) {
		super(errorString, errorObject);
	}
	@Override
	public String getMessage() {
		//TODO with property parameter for i18n
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getErrorString());
		return stringBuffer.toString();
	}
	public String getErrorField() {
		// TODO Auto-generated method stub
		return getErrorObject().toString();
	}
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
