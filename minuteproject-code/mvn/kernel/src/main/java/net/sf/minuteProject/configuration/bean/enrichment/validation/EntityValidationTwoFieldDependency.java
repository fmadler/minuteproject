package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityValidationTwoFieldDependency extends EntityValidationTwoField {
	
	private String operand; //IS_MANDATORY_IF_FIRST_NOT_NULL, IS_NULL_IF_FIRST_NOT_NULL todo change into enum
	
}
