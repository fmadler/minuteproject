package net.sf.minuteProject.exception;

import org.apache.log4j.Logger;

public class MinuteProjectException extends Exception {

	private String message, error;

    public static void throwException (Class clazz, Exception e, String error) throws MinuteProjectException {
    	Logger logger = Logger.getLogger(clazz);
		logger.error(error);
		MinuteProjectException mpe = new MinuteProjectException();
		mpe.setError(error);
		if (e!=null)
			mpe.setMessage(e.getMessage());
		throw mpe;
    }
    
    public static void throwException (Class clazz, String error) throws MinuteProjectException {
    	throwException(clazz, null, error);
    }
    
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public MinuteProjectException(String error) {
		super();
		this.message = error;
		this.error = error;
	}
	
	public MinuteProjectException(String message, String error) {
		super();
		this.message = message;
		this.error = error;
	}

	public MinuteProjectException() {
		super();
	}

}
