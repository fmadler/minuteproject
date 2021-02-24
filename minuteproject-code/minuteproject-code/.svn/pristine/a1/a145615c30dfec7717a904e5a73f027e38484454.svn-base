package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.convention.Conventions;
import net.sf.minuteProject.configuration.bean.enrichment.convention.ModelConvention;

public class BusinessModelEnrichment extends Enrichment<BusinessModel> {
	
	private List <Entity> entities;
	private List <Package> packages;
	
	public void setEntity (Entity entity) {
		addEntity(entity);
	}
	
	public void addEntity (Entity entity) {
		entity.setEnrichment(this);
		getEntities().add(entity);
	}

	public List<Entity> getEntities() {
		if (entities==null)
			entities = new ArrayList<Entity> ();
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public void setPackage (Package pack) {
		addPackage(pack);
	}
	
	public void addPackage (Package pack) {
		pack.setEnrichment(this);
		getPackages().add(pack);
	}

	public List<Package> getPackages() {
		if (packages==null)
			packages = new ArrayList<Package> ();
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
	
	public void applyConventions() {
		for (ModelConvention convention : getConventions().getModelConventions()) {
			convention.apply (this.getModel());
		}	
	}
}
