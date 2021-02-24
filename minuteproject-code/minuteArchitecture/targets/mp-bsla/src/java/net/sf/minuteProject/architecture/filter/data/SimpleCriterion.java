package net.sf.minuteProject.architecture.filter.data;

public class SimpleCriterion extends ClauseCriterion{

	private String field, value, operand;
	private boolean isQuoted;
	@Override
	public String getExpression() {
		// TODO Auto-generated method stub
		if (!isQuoted)
			return field+" "+operand+" "+value;
		return field+" "+operand+" '"+value+"'";
	}
	
	public SimpleCriterion (String field, String value, String operand, boolean isQuoted) {
		this.field = field;
		this.value = value;
		this.operand = operand;
		this.isQuoted = isQuoted;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

	public boolean isQuoted() {
		return isQuoted;
	}

	public void setQuoted(boolean isQuoted) {
		this.isQuoted = isQuoted;
	}



}
