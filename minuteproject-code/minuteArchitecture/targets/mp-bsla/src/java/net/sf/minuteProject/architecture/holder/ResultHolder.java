package net.sf.minuteProject.architecture.holder;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.architecture.holder.element.InformationMessage;
import net.sf.minuteProject.architecture.holder.element.ValidationError;

public class ResultHolder {

	private ValidationHolder validationHolder;
	private InformationHolder informationHolder;
	private Object object;
	

	public void complement(ResultHolder resultHolder) {
		for (ValidationError validationError : resultHolder.getValidationHolder().getValidationErrors()) {
			// this.getValidationHolder().add(validationError);
			addValidationError(validationError);
		}
		for (InformationMessage informationMessage : resultHolder.getInformationHolder().getMessages()) {
			addInformationMessage(informationMessage);
		}
	}

	public void addValidationError(ValidationError validationError) {
		getValidationHolder().add(validationError);
	}

	public void addInformationMessage(InformationMessage informationMessage) {
		getInformationHolder().add(informationMessage);
	}

	public Object getObject() {
		return object;
	}

	public ResultHolder putObject(Object object) {
		this.setObject(object);
		return this;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public ValidationHolder getValidationHolder() {
		if (validationHolder == null)
			validationHolder = new ValidationHolder();
		return validationHolder;
	}

	public void setValidationHolder(ValidationHolder validationHolder) {
		this.validationHolder = validationHolder;
	}

	public InformationHolder getInformationHolder() {
		if (informationHolder == null)
			informationHolder = new InformationHolder();
		return informationHolder;
	}

	public void setInformationHolder(InformationHolder informationHolder) {
		this.informationHolder = informationHolder;
	}


}
