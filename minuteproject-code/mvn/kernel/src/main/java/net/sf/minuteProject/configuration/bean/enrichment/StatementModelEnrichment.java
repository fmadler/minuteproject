package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.enrichment.convention.Conventions;
import net.sf.minuteProject.configuration.bean.enrichment.convention.SddConvention;

public class StatementModelEnrichment extends Enrichment<StatementModel> {

	public void applyConventions() {
		for (SddConvention convention : getConventions().getStatementConventions()) {
			convention.apply (this.getModel());
		}	
	}
}
