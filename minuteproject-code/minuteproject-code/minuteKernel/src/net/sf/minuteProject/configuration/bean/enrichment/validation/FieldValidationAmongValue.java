package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Data;

@Data
public class FieldValidationAmongValue extends ValidationBean implements FieldValidation {

	private String values;
	
}
