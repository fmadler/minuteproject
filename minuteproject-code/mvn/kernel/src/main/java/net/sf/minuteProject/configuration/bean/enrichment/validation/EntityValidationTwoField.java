package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString// (callSuper=call)
public class EntityValidationTwoField implements EntityValidation {

	private String firstFieldName;
	private String secondFieldName;
}
