package net.sf.minuteProject.architecture.filter.data;

public class ClauseExpression extends Expression{

	private StringBuffer expression = new StringBuffer();
	@Override
	public String getExpression() {
		return expression.toString();
	}

	public void addClauseCriterion (ClauseCriterion clauseCriterion, LogicalWord logicalWord) {
		expression.append(clauseCriterion.getExpression());
		expression.append(logicalWord.getWord());
	}
	
	public void addClauseCriterion (ClauseCriterion clauseCriterion) {
		expression.append(clauseCriterion.getExpression());
	}
}
