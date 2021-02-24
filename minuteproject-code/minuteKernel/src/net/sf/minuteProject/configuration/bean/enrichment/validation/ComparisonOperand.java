package net.sf.minuteProject.configuration.bean.enrichment.validation;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum ComparisonOperand {
    EQUALS ("EQUALS", "first-field-equals-second-field")
    ,ENDS_WITH ("ENDS_WITH", "ends-with")
    ,STARTS_WITH ("STARTS_WITH", "starts-with")
    ,GREATER_THAN ("GREATER_THAN", "first-field-greater-than-second-field")
    ,GREATER_OR_EQUAL_THAN ("GREATER_OR_EQUAL_THAN", "first-field-greater-or-equals-than-second-field")
    ,LESS_THAN ("LESS_THAN", "first-field-less-than-second-field")
    ,LESS_OR_EQUAL_THAN ("LESS_OR_EQUAL_THAN", "first-less-or-equal-than-second-field")
    ,SECOND_MANDATORY_WHEN_FIRST_FILLED ("SECOND_MANDATORY_WHEN_FIRST_FILLED")
    
    ,FIELD_GROUP ("FIELD_GROUP","field-group")
    ;
	
	private final String value, conventionValue;
	
	ComparisonOperand(String value) {
		this(value, null);
	}
	
	ComparisonOperand(String value, String conventionValue) {
		this.value = value;
		this.conventionValue = conventionValue;
	}

	public static Optional<ComparisonOperand> fromConventionValue(String type) {
		return Arrays.asList(ComparisonOperand.values()).stream()
			.filter(u -> u.getConventionValue()!=null && u.getConventionValue().equals(type))
			.findFirst()
			;
		
	}
}
