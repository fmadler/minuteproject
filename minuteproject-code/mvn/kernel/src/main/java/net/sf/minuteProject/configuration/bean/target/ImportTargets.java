package net.sf.minuteProject.configuration.bean.target;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class ImportTargets extends AbstractConfiguration {

	private List<ImportTarget> importTargets;

	public List<ImportTarget> getImportTargets() {
		if (importTargets==null)
			return new ArrayList<ImportTarget>();
		return importTargets;
	}

	public void setImportTargets(List<ImportTarget> importTargets) {
		this.importTargets = importTargets;
	}
	
	public void addImportTarget (ImportTarget importTarget) {
		getImportTargets().add(importTarget);
	}
	
}
