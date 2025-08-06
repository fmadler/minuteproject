package net.sf.minuteProject.configuration.bean.model.statement;

import lombok.Getter;
import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enumeration.Cardinality;

@Getter
public class CompositeQueryElement extends AbstractConfiguration{

	private Query query;
	private Composite composite;
	private Cardinality cardinality = Cardinality.MANY;

	public Query getQuery() {
		if (query==null)
			query = retrieveQuery();
		return query;
	}

	private Query retrieveQuery() {
		for (Query query : composite.getComposites().getStatementModel().getQueries().getQueries()) {
			if (query.getId()!=null && query.getId().equals(refid))
				return query;
		}
		return null;
	}

	public void setCardinality(String cardinality) {
		this.cardinality = Cardinality.valueOf(cardinality.toUpperCase());
	}

	public void setCard(String cardinality) {
		setCardinality(cardinality);
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public void setComposite(Composite composite) {
		this.composite = composite;
	}
	
}
