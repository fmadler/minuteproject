package net.sf.minuteProject.architecture.filter.data;

import org.apache.commons.lang.StringUtils;

public class CaseInsensitiveCriterion extends SimpleCriterion{

	public CaseInsensitiveCriterion (String field, String value, String operand) {
		super(field, value, operand, true);
	}
	
	public String getExpression() {
		//Attention hibernate implementation
		return "lower("+getField()+")"+getOperand()+" '"+StringUtils.lowerCase(getValue())+"'";
	}
}
