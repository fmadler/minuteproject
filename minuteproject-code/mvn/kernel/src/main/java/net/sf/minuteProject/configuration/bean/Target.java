package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.system.Plugin;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.configuration.bean.target.ImportTargets;
import net.sf.minuteProject.configuration.bean.target.TargetParams;
import net.sf.minuteProject.utils.io.FileUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

/**
 * @author Florian Adler
 * 
 */
public class Target extends AbstractConfiguration {

	private String dir;
	private String fileName;
	private String canonicalDir;
	private String canonicalFileName;
	private ArchitectureTarget architectureTarget;
	private List<TemplateTarget> templateTargets;
	private List<ResourceTarget> resourceTargets;
	private AbstractConfigurationRoot abstractConfigurationRoot;
	private List<Target> dependency;
	private List<Plugin> plugins;
	private TargetParams targetParams;
	private ImportTargets importTargets;
	private String outputdirRoot;
	private String templatedirRoot, templatedirRef;
	private Boolean isGenerable, isFromCatalog = false;
	private List<String> templatedirRefs;
	private Targets targets;

	public void complement(Target target) {
		if (abstractConfigurationRoot == null)
			abstractConfigurationRoot = target.getAbstractConfigurationRoot();
		List<TemplateTarget> input = target.getTemplateTargets();
		for (TemplateTarget templateTarget : input) {
			templateTarget.setTarget(this);
			templateTarget.setIsGenerable(target.isGenerable());
			templateTarget.setRootdir(target.getTemplatedirRoot());
			templateTarget.getTemplatedirRefs().addAll(
					target.getTemplatedirRefs());

			templateTarget.getProperties().addAll(target.getProperties());

			getTemplateTargets().add(templateTarget);

			for (Template template : templateTarget.getTemplates()) {
				template.setOutputdirRoot(target.getOutputdirRoot());
				template.getProperties().addAll(target.getProperties());
			}
		}
		List<ResourceTarget> resources = target.getResourceTargets();
		for (ResourceTarget resourceTarget : resources) {
			resourceTarget.setTarget(this);
			resourceTarget.setIsGenerable(target.isGenerable());
			resourceTarget.setRootdir(target.getTemplatedirRoot());
			resourceTarget.getProperties().addAll(target.getProperties());
			
			getResourceTargets().add(resourceTarget);

			for (Resource resource : resourceTarget.getResources()) {
				resource.setOutputdirRoot(target.getOutputdirRoot());
				resource.getProperties().addAll(target.getProperties());
			}
		}
		getPlugins().addAll(target.getPlugins());
	}

	public AbstractConfigurationRoot getAbstractConfigurationRoot() {
		return abstractConfigurationRoot;
	}

	public void setAbstractConfigurationRoot(
			AbstractConfigurationRoot abstractConfigurationRoot) {
		this.abstractConfigurationRoot = abstractConfigurationRoot;
	}

	public ArchitectureTarget getArchitectureTarget() {
		return architectureTarget;
	}

	public void setArchitectureTarget(ArchitectureTarget architectureTarget) {
		this.architectureTarget = architectureTarget;
	}

	public Template getTemplate(String name) {
		List list = getTemplateTargets();
		for (int i = 0; i < list.size(); i++) {
			TemplateTarget templateTarget = (TemplateTarget) list.get(i);
			Template template;
			if ((template = templateTarget.getTemplate(name)) != null)
				return template;
		}
		return null;
	}

	public void addTemplateTarget(TemplateTarget templateTarget) {
		// if (templateTargets==null)
		// templateTargets = new ArrayList<TemplateTarget>();
		templateTarget.setTarget(this);
		getTemplateTargets().add(templateTarget);
	}

	public List<TemplateTarget> getTemplateTargets() {
		if (templateTargets == null)
			templateTargets = new ArrayList<TemplateTarget>();
		return templateTargets;
	}

	public List<TemplateTarget> getOrderedByPriorityTemplateTargets() {
		Collections.sort(getTemplateTargets());
		return templateTargets;
	}

	public void setTemplateTargets(List<TemplateTarget> templateTargets) {
		this.templateTargets = templateTargets;
	}

	public void addDependency(String dependencies) {
		if (getDependency() == null)
			setDependency(new ArrayList<Target>());
		// Target target = getTarget
	}

	public List<Target> getDependency() {
		return dependency;
	}

	private void setDependency(List<Target> dependency) {
		this.dependency = dependency;
	}

	public List<String> getAbsoluteRootDirs(String rootDir) {
		List<String> l = new ArrayList<String>();
		for (String s : ParserUtils.getList(rootDir))
			l.add(FileUtils.getAbsoluteDir(s, s, getTemplatedirRoot()));
		return l;
	}

	public String getAbsoluteRootDir(String rootDir) {
		return FileUtils.getAbsoluteDir(rootDir, rootDir, getTemplatedirRoot());
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFileName() {
		// return FileUtils.stripRelativePath(fileName);
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void addPlugin(Plugin plugin) {
		getPlugins().add(plugin);
	}

	public List<Plugin> getPlugins() {
		if (plugins == null)
			setPlugins(new ArrayList<Plugin>());
		return plugins;
	}

	public void setPlugins(List<Plugin> plugins) {
		this.plugins = plugins;
	}

	public String getCanonicalDir() {
		return canonicalDir;
	}

	public void setCanonicalDir(String canonicalDir) {
		this.canonicalDir = canonicalDir;
	}

	public String getCanonicalFileName() {
		return canonicalFileName;
	}

	public void setCanonicalFileName(String canonicalFileName) {
		this.canonicalFileName = canonicalFileName;
	}

	public ImportTargets getImportTargets() {
		return importTargets;
	}

	public void setImportTargets(ImportTargets importTargets) {
		this.importTargets = importTargets;
	}

	public TargetParams getTargetParams() {
		return targetParams;
	}

	public void setTargetParams(TargetParams targetParams) {
		this.targetParams = targetParams;
	}

	public String getOutputdirRoot() {
		// TODO cache
		StringBuffer sb = new StringBuffer();
		if (hasAppendOutputDir()) {
			sb.append(getTargets().getOutputdirRoot());
		}
		if (outputdirRoot != null) {
			if (hasAppendOutputDir())
				sb.append("/");
			sb.append(outputdirRoot);
		}
		return sb.toString();
	}

	private boolean hasAppendOutputDir() {
		return hasTargetsOutputdir() && !isFromCatalog;
	}

	private boolean hasTargetsOutputdir() {
		return (getTargets() != null && !StringUtils.isEmpty(getTargets()
				.getOutputdirRoot()));
	}

	// private boolean hasTargetsOutputdirDirAppend() {
	// return (getTargets()!=null &&
	// getTargets().getAppendCatalogEntryDirToOutputDirRoot()) ;
	// }

	public void setOutputdirRoot(String outputdirRoot) {
		this.outputdirRoot = outputdirRoot;
	}

	public String getTemplatedirRoot() {
		return templatedirRoot;
	}

	public void setTemplatedirRoot(String templatedirRoot) {
		this.templatedirRoot = templatedirRoot;
	}

	public String getTemplatedirRef() {
		return templatedirRef;
	}

	public List<String> getTemplatedirRefs() {
		if (templatedirRefs == null) {
			templatedirRefs = new ArrayList<String>();
			if (templatedirRef != null)
				templatedirRefs.add(templatedirRef);
		}
		return templatedirRefs;
	}

	public void addTemplatedirRef(String templatedirRef) {
		if (templatedirRef != null)
			getTemplatedirRefs().add(templatedirRef);
	}

	public void setTemplatedirRef(String templatedirRef) {
		this.templatedirRef = templatedirRef;
	}

	public boolean isGenerable() {
		if (isGenerable == null)
			isGenerable = true;
		return isGenerable;
	}

	public void setIsGenerable(Boolean isGenerable) {
		this.isGenerable = isGenerable;
	}

	public void complementAdditional(Target target) {
		getTemplatedirRefs().addAll(target.getTemplatedirRefs());
	}

	public String getPropertyValue(String name) {
		String s = super.getPropertyValue(name);
		if (s != null)
			return s;
		if (targets != null)
			return targets.getPropertyValue(name);
		return null;
	}

	public String getTargetPropertyValue(String name) {
		String s = getPropertyValue(name);
		if (s != null)
			return s;
		if (targets != null)
			return targets.getPropertyValue(name);
		if (abstractConfigurationRoot != null)
			return abstractConfigurationRoot.getPropertyValue(name);
		return null;
	}

	public Property getTemplateTargetPropertyByName(String name) {
		Property p = getPropertyByName(name);
		if (p != null)
			return p;
		if (targets != null)
			return targets.getPropertyByName(name);
		if (abstractConfigurationRoot != null)
			return abstractConfigurationRoot.getPropertyByName(name);
		return null;
	}

	public Targets getTargets() {
		return targets;
	}

	public void setTargets(Targets targets) {
		this.targets = targets;
	}

	public Boolean getIsGenerable() {
		return isGenerable;
	}

	public Boolean getIsFromCatalog() {
		return isFromCatalog;
	}

	public void setIsFromCatalog(Boolean isFromCatalog) {
		this.isFromCatalog = isFromCatalog;
	}

	public List<ResourceTarget> getResourceTargets() {
		if (resourceTargets == null)
			resourceTargets = new ArrayList<ResourceTarget>();
		return resourceTargets;
	}

	public void addResourceTarget(ResourceTarget resourceTarget) {
		resourceTarget.setTarget(this);
		getResourceTargets().add(resourceTarget);
	}

}
