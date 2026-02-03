package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.Action;

public class NestedStatement extends AbstractConfiguration{

	private List<Action> actions;
	private NestedStatements nestedStatements;
	private String queryId;

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public NestedStatements getNestedStatements() {
		return nestedStatements;
	}

	public void setNestedStatements(NestedStatements nestedStatements) {
		this.nestedStatements = nestedStatements;
	}
	
	public List<Action> getActions() {
		if (actions==null) actions = new ArrayList<Action>();
		return actions;
	}
	
	public void addAction (Action action) {
		getActions().add(action);
	}

}
