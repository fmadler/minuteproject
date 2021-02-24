package net.sf.minuteProject.architecture.holder;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.architecture.holder.element.ValidationError;

public class ValidationHolder {
	
	private List<ValidationError> validationErrors;
	
	public void add(ValidationError validationError) {
		if (validationError!=null)
			getValidationErrors().add(validationError);
	}

	public List<ValidationError> getValidationErrors() {
		if (validationErrors==null)
			validationErrors = new ArrayList<ValidationError>();
		return validationErrors;
	}

	public void setValidationErrors(List<ValidationError> validationErrors) {
		this.validationErrors = validationErrors;
	}


	public boolean isValid() {
		return getValidationErrors().isEmpty();
	}
	
}
