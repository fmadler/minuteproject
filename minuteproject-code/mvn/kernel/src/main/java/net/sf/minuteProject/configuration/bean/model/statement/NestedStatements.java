package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.StatementModel;

public class NestedStatements extends AbstractConfiguration{

	public static final String DEFAULT_PACKAGE_NAME = "Nested_Statement";

	private StatementModel statementModel;
	
	private List<NestedStatement> nestedStatement;

	public StatementModel getStatementModel() {
		return statementModel;
	}

	public void setStatementModel(StatementModel statementModel) {
		this.statementModel = statementModel;
	}

	public List<NestedStatement> getNestedStatements() {
		if (nestedStatement==null)
			nestedStatement = new ArrayList<NestedStatement>();
		return nestedStatement;
	}

	public void setNestedStatements(List<NestedStatement> nestedStatement) {
		this.nestedStatement = nestedStatement;
	}
	
	public void addNestedStatement (NestedStatement nestedStatement) {
		nestedStatement.setNestedStatements(this);
		getNestedStatements().add(nestedStatement);
	}
}
