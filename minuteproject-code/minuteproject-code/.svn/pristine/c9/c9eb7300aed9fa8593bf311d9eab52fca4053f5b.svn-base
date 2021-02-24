package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.system.GenerationAction;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.io.FileUtils;

import org.apache.commons.lang.StringUtils;

/**
 * @author Florian Adler
 *
 */
public class Targets extends AbstractConfiguration {
	
	private static final String MP_HOME = "MP_HOME";
	private String outputdirRoot, catalog, catalogEntry, templatedirRoot;
	private Boolean appendCatalogEntryDirToOutputDirRoot=false;
	public static final String deliveryPath = "../../template";
	public static final String developmentPath = "../minuteTemplate/template";
	public static final String deliveryResourcePath = "../../resources/icon";
	public static final String developmentResourcePath = "../../minuteResource/icon";
	public static final String MP_HOME_RELATIVE_PATH = "/template";
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

//	public String getOutputdirRoot() {
//		if (hasCatalogEntry() && outputdirRoot==null) {
//			String output = FileUtils.exists(deliveryPath)?"../output/":"../../output/";
//			outputdirRoot = output+getCatalogEntry()+"/"+((Configuration)getAbstractConfigurationRoot()).getModel().getName(); // default
//		}
//		return outputdirRoot;
//	}

	public String getOutputdirRoot() {
		return outputdirRoot;
//		return getOutputdirRootCache(getCatalogEntry());
	}
/*
	public String getOutputdirRootCache(String subdir) {
		if (hasCatalogEntry() && outputdirRoot==null) {
			outputdirRoot = getOutputdirRoot(subdir);
		}
		return outputdirRoot;
	}
*/	
	public String getOutputdirRoot(String subdir) {
		String output = FileUtils.exists(deliveryPath)?getOutputdir("../output/"):getOutputdir("../../output/");
		final Configuration configuration = (Configuration)getAbstractConfigurationRoot();
		String name = (configuration.isSingleModel())?configuration.getModel().getName():configuration.getApplication().getName();
		return output+subdir+"/"+name; // default
	}
	
	public String getOutputdir(String defaultOuputdir) {
		if (appendCatalogEntryDirToOutputDirRoot && outputdirRoot!=null) {
			return outputdirRoot; //TODO check ends with "/"
		}
		return defaultOuputdir;
	}
	
	private boolean hasCatalogEntry() {
		return !StringUtils.isEmpty(catalogEntry);
	}

	public void setOutputdirRoot(String outputdirRoot) {
		this.outputdirRoot = outputdirRoot;
	}

	public String getTemplatedirRoot() throws MinuteProjectException {
		if (hasCatalogEntry() && templatedirRoot==null) {
			// try default for release (use by console)
			if (FileUtils.exists(deliveryPath))
				return deliveryPath;
			// try default for dev (used in dev, MP as a workspace)
			if (FileUtils.exists(developmentPath))
				return developmentPath;			
			// try from MP_HOME (used by integration tool such as ANT)
			String MpHomePath = System.getProperty(MP_HOME);
			if (MpHomePath!=null && FileUtils.exists(MpHomePath))
				return MpHomePath+MP_HOME_RELATIVE_PATH;			
			throw new MinuteProjectException("targets node with catalog entry does not seems to have correct template association");
		}
		return templatedirRoot;
	}
	
	public static String getResourcedirRoot() throws MinuteProjectException {
		// try default for release (use by console)
		if (FileUtils.exists(deliveryResourcePath))
			return deliveryResourcePath;
		return developmentResourcePath;			
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

	public Boolean getAppendCatalogEntryDirToOutputDirRoot() {
		return appendCatalogEntryDirToOutputDirRoot;
	}

	public void setAppendCatalogEntryDirToOutputDirRoot(
			Boolean appendCatalogEntryDirToOutputDirRoot) {
		this.appendCatalogEntryDirToOutputDirRoot = appendCatalogEntryDirToOutputDirRoot;
	}

	
}
