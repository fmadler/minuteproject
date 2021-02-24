package net.sf.minuteProject.model.rest;

import java.io.Serializable;

public class GenericResult implements Serializable {

	private static final long serialVersionUID = -8352393493427827079L;

	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}