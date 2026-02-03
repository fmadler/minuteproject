package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.InstanceofPredicate;
import org.apache.commons.collections.functors.PredicateTransformer;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.Enrichment;

public class Conventions extends AbstractConfiguration{

//	private Enrichment enrichment;
	public List<Convention> conventions;
	
	private List<Convention> getConventions() {
		if (conventions == null) conventions = new ArrayList<Convention>();
		return conventions;
	}
	
	public  Collection<Convention> getConventionsByType(Class type) {
		Transformer transformer = new PredicateTransformer(new InstanceofPredicate(type.getClass()));
		return CollectionUtils.transformedCollection(getConventions(), transformer);
	}
	
	public Collection<ModelConvention> getModelConventions() {
		Transformer transformer = new PredicateTransformer(new InstanceofPredicate(ModelConvention.class));
		return CollectionUtils.transformedCollection(getConventions(), transformer);
	}
	
	public Collection<KernelConvention> getKernelConventions() {
		Transformer transformer = new PredicateTransformer(new InstanceofPredicate(KernelConvention.class));
		return CollectionUtils.transformedCollection(getConventions(), transformer);
	}
	
	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}
//	public Enrichment getEnrichment() {
//		return enrichment;
//	}
//	public void setEnrichment(Enrichment enrichment) {
//		this.enrichment = enrichment;
//	}
	
	public void addConvention (Convention convention) {
		getConventions().add(convention);
	}

	public Collection<SddConvention> getStatementConventions() {
		Transformer transformer = new PredicateTransformer(new InstanceofPredicate(SddConvention.class));
		return CollectionUtils.transformedCollection(getConventions(), transformer);
	}

	public boolean isBusinessModelGenerationDisable() {
		for (KernelConvention c :getKernelConventions() ) {
			if (TargetConvention.DISABLE_BUSINESS_MODEL_GENERATION.equals(c.getType()))
				return true;
		}
		return false;
	}
	
//	public void addConvention (ViewPrimaryKeyConvention convention) {
//		getConventions().add(convention);
//	}	
	
//	public void applyConventions() {
//		for (Convention<T> convention : getConventions()) {
//			convention.apply (this.get);
//		}	
//	}
}
