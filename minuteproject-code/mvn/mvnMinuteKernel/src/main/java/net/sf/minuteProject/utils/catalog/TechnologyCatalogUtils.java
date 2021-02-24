package net.sf.minuteProject.utils.catalog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Framework;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technology;
import net.sf.minuteProject.utils.io.FileUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;
import net.sf.minuteProject.utils.technology.TechnologyUtils;

public class TechnologyCatalogUtils extends CatalogUtils{

	private static List<Technology> technologies;
	
	public static Technology getPublishedTechnology(String name, String catalogDir) throws MinuteProjectException {
		for (Technology technology : getPublishedTechnologies (catalogDir)) {
			if (technology.getName().equals(name))
				return technology;
		}		
		return null;
	}
	
	public static List<Technology> getPublishedTechnologies (String catalogDir) throws MinuteProjectException {
		if (technologies==null)
			technologies = getPublishedTechnologyCatalogHolder(catalogDir).getTechnologyCatalog().getTechnologies().getTechnologys();
		return technologies;
	}
	
	public static String[] getPublishedTechnologyNames(String catalogDir) throws MinuteProjectException {
		List<String> list = new ArrayList<String>();
		for (Technology technology : getPublishedTechnologies(catalogDir)) {
			if (technology.isGenerable())
				list.add(technology.getName());
		}
		return (String[])list.toArray(new String[list.size()]);
	}
	
	public static List<Technology> getDependentTechnologies (Technology technology, String catalogDir) throws MinuteProjectException {
		List<Technology> list = new ArrayList<Technology>();
		List<String> targets = ParserUtils.getList(technology.getDependsOnTargets());
		for (String targetName : targets) {
			Technology tech = getTechnologyByName(targetName, catalogDir);
			if (tech!=null)
				list.add(tech);
		}
		return list;
	}

	public static Technology getTechnologyByName(String name, String catalogDir) throws MinuteProjectException {
		for (Technology technology : getPublishedTechnologies(catalogDir)) {
			if (technology.getName().equals(name))
				return technology;
		}		
		return null;
	}

	public static List<Technology> getAllRelatedTechnologies (Technology technologyRoot, String catalogDir) throws MinuteProjectException {
		List<Technology> list = new ArrayList<Technology>();
		List<Technology> allTechnos = getPublishedTechnologies(catalogDir);
		List<Technology> dependentTechnologies = getDependentTechnologies (technologyRoot, catalogDir);
		for (Technology technology2 : allTechnos) {
			if (isDependent(technologyRoot, technology2, dependentTechnologies)) {
				technology2.getProperties().addAll(technologyRoot.getProperties());
				list.add(getDependentTechnology(technology2, true));
			} else {
				// all the other technology are in the path (it is to remove reference to lib
				list.add(getDependentTechnology(technology2, false));
			}
		}
		return list;
	}

	private static boolean isDependent(Technology technologyroot, Technology technology, List<Technology> dependentTechnologies) {
		if (technologyroot==technology) return true;
		for (Technology technology2 : dependentTechnologies) {
			if (technology.equals(technology2))
				return true;
		}
		return false;
	}

	private static Technology getDependentTechnology(Technology technology2, boolean isGenerable) {
//
//		if (technology2.isGenerable() && !isGenerable)
//			technology2.setIsGenerable(false);		
		if (!isGenerable)
			technology2.setIsGenerable(false);
		else
			technology2.setIsGenerable(true);
		return technology2;
	}
	
	public static void resetTechnologies () {
		technologies = null;
		CatalogUtils.resetTechnologycatalogHolder();
	}

	public static String[][] getFrameworkDependency(Technology technology, String catalogDir) throws MinuteProjectException {
		List<Framework> frameworks = getAllLinkedFrameworkDependencies(technology, catalogDir);
		String [][] array = new String [frameworks.size()][2];
		int i=0;
		for (Framework framework : frameworks) {
			array[i][0]=framework.getName();
			array[i][1]=framework.getVersion();
			i++;
		}
		return array;
	}
	
	public static List<Framework> getAllLinkedFrameworkDependencies(Technology technology, String catalogDir) throws MinuteProjectException {
		List<Framework> frameworks = new ArrayList<Framework>();
		frameworks.addAll(getFrameworkDependencies(technology));
		frameworks.addAll(getRelatedFrameworkDependencies(technology, catalogDir));
		return frameworks;
	}
	
	public static List<Framework> getFrameworkDependencies(Technology technology) {
		List<Framework> frameworks = new ArrayList<Framework>();
		for (Framework framework : technology.getFrameworks().getFrameworks()) {
			frameworks.add(framework);
		}
		return frameworks;
	}
	
	public static List<Framework> getRelatedFrameworkDependencies(Technology technology, String catalogDir) throws MinuteProjectException {
		List<Framework> frameworks = new ArrayList<Framework>();
		List<Technology> technos = getDependentTechnologies(technology, catalogDir);
		for (Technology technology2 : technos) {
			frameworks.addAll(getFrameworkDependencies(technology2));
		}
		return frameworks;
	}
	
	//TARGETS
	public static Targets getTargets(String technologyTarget, String catalogDir,String outputDir, String templateRootDir) throws MinuteProjectException {
		return getTargets(getChoosenTechnology(technologyTarget, catalogDir), catalogDir, outputDir, templateRootDir);
	}
	

	private static Technology getChoosenTechnology(String targetTechnology, String catalogDir) throws MinuteProjectException {
	    return TechnologyCatalogUtils.getPublishedTechnology(targetTechnology, catalogDir);
	}
	
	public static Targets getTargets(Technology technologyTarget, String catalogDir,String outputDir, String templateRootDir) throws MinuteProjectException {
		Targets targets = new Targets();
		for (Target target2 : getAllRelatedTargets(technologyTarget, catalogDir, outputDir, templateRootDir)) {
			targets.addTarget(target2);
		}
		return targets;
	}
	
	private static List<Target> getAllRelatedTargets(Technology technologyTarget, String catalogDir, String outputDir, String templateRootDir) throws MinuteProjectException {
		List<Target> list = new ArrayList<Target>();
		List<Technology> technologies = TechnologyCatalogUtils.getAllRelatedTechnologies(technologyTarget, catalogDir);
		for (Technology technology : technologies) {
//			//add properties
//			technology.getProperties().addAll(getChoosenTechnology().getProperties());
			list.add(getTarget(technology, catalogDir, outputDir, templateRootDir, true));
		}
		return list;
	}
	
	private static Target getTarget(Technology technology, String catalogDir, String outputDir, String templateRootDir, boolean isDefaultOutputToAppend) {
		Target target = new Target();
		target.setName(technology.getName());
		target.setProperties(technology.getProperties());
		target.setFileName(technology.getTemplateConfigFileName());
		target.setDir(getTemplateSetFullPath(technology.getTemplateConfigFileName(), catalogDir));
		target.setTemplatedirRoot(TechnologyUtils.getTechnologyTemplateDir(technology, templateRootDir));		
		target.setOutputdirRoot(getOutputDir(technology, outputDir, isDefaultOutputToAppend));
		target.setIsGenerable(technology.isGenerable());
		target.setIsFromCatalog(true);
		return target;
	}

	private static String getTemplateSetFullPath(String fileName, String catalogDir) {
		String canonicalFileName = getTemplateSetFullPathAndFileName(fileName, catalogDir);
		return StringUtils.removeEnd(canonicalFileName, fileName);
	}
	
	private static String getTemplateSetFullPathAndFileName(String fileName, String catalogDir) {
		return FileUtils.getFileFullPathFromFileInRootClassPath(catalogDir+"/"+ fileName);
	}
	
	public static String getOutputDir(Technology technology, String outputDir, boolean isDefaultOutputToAppend) {
		if (isDefaultOutputToAppend)
			return outputDir+"/"+technology.getDefaultOutputdir();
		return outputDir;
	}
}
