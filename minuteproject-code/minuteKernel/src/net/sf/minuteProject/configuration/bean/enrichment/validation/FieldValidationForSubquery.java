package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Data;

@Data
public class FieldValidationForSubquery implements FieldValidation {
	public boolean isAutonomous () {
		return false;
	}
	private String queryId;
	private String queryParams;
}
