package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.system.GenerationAction;

import org.apache.commons.lang.StringUtils;

/**
 * @author Florian Adler
 *
 */
public class Targets extends AbstractConfiguration {
	
	private String outputdirRoot, catalog, catalogEntry, templatedirRoot;

	private AbstractConfigurationRoot abstractConfigurationRoot;
	private List<Target> targets;
	private GenerationAction postGenerationAction;

	public List<Target> getTargets() {
		if (targets==null)
			targets = new ArrayList<Target>();
		return targets;
	}

	public void setTargets(List<Target> targets) {
		this.targets = targets;
	}
	
	public AbstractConfigurationRoot getAbstractConfigurationRoot() {
		return abstractConfigurationRoot;
	}
	public void setAbstractConfigurationRoot(
			AbstractConfigurationRoot abstractConfigurationRoot) {
		this.abstractConfigurationRoot = abstractConfigurationRoot;
	}
	
	public void addTarget(Target target) {
		target.setTargets(this);
		target.setAbstractConfigurationRoot(getAbstractConfigurationRoot());
		getTargets().add(target);
	}

	public String getOutputdirRoot() {
		if (hasCatalogEntry() && outputdirRoot==null)
			return "../../output/"+getCatalogEntry(); // default
		return outputdirRoot;
	}

	private boolean hasCatalogEntry() {
		return !StringUtils.isEmpty(catalogEntry);
	}

	public void setOutputdirRoot(String outputdirRoot) {
		this.outputdirRoot = outputdirRoot;
	}

	public String getTemplatedirRoot() {
		if (hasCatalogEntry() && templatedirRoot==null) 
			return "template";//default : search in classpath
		return templatedirRoot;
	}

	public void setTemplatedirRoot(String templatedirRoot) {
		this.templatedirRoot = templatedirRoot;
	}

	public GenerationAction getPostGenerationAction() {
		return postGenerationAction;
	}

	public void setPostGenerationAction(GenerationAction postGenerationAction) {
		postGenerationAction.setTargets(this);
		this.postGenerationAction = postGenerationAction;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	
	public String getCatalogEntry() {
		return catalogEntry;
	}

	public void setCatalogEntry(String catalogEntry) {
		this.catalogEntry = catalogEntry;
	}

	
}
