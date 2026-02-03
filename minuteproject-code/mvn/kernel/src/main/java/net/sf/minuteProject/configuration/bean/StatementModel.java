package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.configuration.bean.enrichment.StatementModelEnrichment;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.statement.Composites;
import net.sf.minuteProject.configuration.bean.model.statement.NestedStatements;
import net.sf.minuteProject.configuration.bean.model.statement.Queries;
import net.sf.minuteProject.configuration.bean.model.statement.Query;

import org.apache.log4j.Logger;

public class StatementModel {
	
	private StatementModelEnrichment enrichment;
	
	private static Logger logger = Logger.getLogger(StatementModel.class);
	private Queries queries;
	private Composites composites;
	private NestedStatements nestedStatements;
	private SDDPackage sddPackage;
	
	private Model model;
	
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Queries getQueries() {
		if (queries==null) {
			queries = new Queries();
		}
		return queries;
	}
	public Composites getComposites() {
		if (composites==null)
			composites = new Composites();
		return composites;
	}
	public SDDPackage getSddPackage() {
		if (sddPackage==null) sddPackage = new SDDPackage(this);
		return sddPackage;
	}
	public void setSddPackage(SDDPackage sddPackage) {
		this.sddPackage = sddPackage;
	}
	public void setQueries(Queries queries) {
		queries.setStatementModel(this);
		this.queries = queries;
	}
	public void setComposites(Composites composites) {
		composites.setStatementModel(this);
		this.composites = composites;
	}
	
	public void complementStatement() {
 		complementStatementPackage();
	}

	public Query getQueryByIdOrName(String id) {
		for (Query query : getQueries().getQueries()) {
			if (query.getId()!=null && query.getId().equals(id))
				return query;
		}
		return getQueryByName(id); // search by name
	}
	
	public Query getQueryByName(String name) {
		if (name!=null) {
			for (Query query : getQueries().getQueries()) {
				if (query.getName().toUpperCase().equals(name.toUpperCase()))
					return query;
			}
		}
		return null;
	}
	
	private void complementStatementPackage() {
		getSddPackage().setPackages(model);
	}
	
	public boolean hasQueries () {
		return (getQueries().getQueries().size()>0)?true:false;
	}
	
	public boolean hasComposites () {
		return (getComposites().getComposites().size()>0)?true:false;
	}
	
	public void applyConventions() {
		if (enrichment!=null && enrichment.getConventions()!=null) {
			enrichment.applyConventions();
		}		
	}
	public StatementModelEnrichment getEnrichment() {
		return enrichment;
	}

	public void setBusinessModelEnrichment(StatementModelEnrichment enrichment) {
		enrichment.setModel(this);
		this.enrichment = enrichment;
	}

	public NestedStatements getNestedStatements() {
		return nestedStatements;
	}
	public void setNestedStatements(NestedStatements nestedStatements) {
		this.nestedStatements = nestedStatements;
	}
}
