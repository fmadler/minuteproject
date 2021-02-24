package net.sf.minuteProject.plugin.maven;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.Application;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.GeneratorQualifier;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.connection.Driver;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.CommonUtils;

public class MavenUtils {
	
//	public class MavenDependency {
//		public String name, groupId, artifactId, version, scope;
//		public boolean isTest () {
//			return StringUtils.isNotBlank(scope) && scope.equalsIgnoreCase("test");
//		}
//	}
	public static final String artifactFinalName = "maven-artifact-finalName-suffix";
	
	public static String getPackaging(Template template) {
		return template.getPropertyValue("maven-packaging", "jar");
	}
	
	public static String getFinalName (Template template, GeneratorQualifier model) {
		return getArtifactId(template,model);
	}
	
	public static String getArtifactId (Template template, GeneratorQualifier model) {
		String finalNameSuffix = template.getPropertyValue(artifactFinalName);
		return  (finalNameSuffix!=null)? model.getName()+finalNameSuffix:model.getName()+"App";
	}
	
	public static String getJarArtifactName (Template template, GeneratorQualifier model) {
		return getArtifactName(template, model, "jar-maven-template", "maven-artifact-finalName-suffix");
	}
	
	public static String getDependentJarArtifactName (Template template, GeneratorQualifier model) {
		return getArtifactName(template, model, "jar-maven-template", "dependent-maven-artifact-finalName-suffix");
	}

	public static String getWebArtifactName (Template template, GeneratorQualifier model) {
		return getArtifactName(template, model, "web-maven-template", "maven-artifact-finalName-suffix");
	}
	
	public static String getEjbArtifactName (Template template, GeneratorQualifier model) {
		return getArtifactName(template, model, "ejb-maven-template", "maven-artifact-finalName-suffix");
	}
	
	public static String getArtifactName (Template template, GeneratorQualifier bean, String mavenTemplate, String mavenArtifactFinalName) {
		//property from catalog
		String markerTemplate = template.getPropertyValue(mavenTemplate);
		if (markerTemplate!=null && bean.getConfiguration()!=null) {
			Template marker = CommonUtils.getTemplate(bean.getConfiguration(), markerTemplate);
			return bean.getName()+marker.getPropertyValue(mavenArtifactFinalName);
		}
		return null;
	}
	
	public static boolean hasMasterPom(Template template, GeneratorQualifier bean) {
		if (bean.getConfiguration()!=null) {
			Template marker = CommonUtils.getTemplate(bean.getConfiguration(), "MavenMasterPomXml");
			return marker!=null;
		}
		return false;
	}
	
	public static String getMasterPomArtifactId(Template template, GeneratorQualifier bean) {
		if (bean.getConfiguration()!=null) {
			Template tmplt = CommonUtils.getTemplate(bean.getConfiguration(), "MavenMasterPomXml");
			return getArtifactId(tmplt, bean);
		}
		return "artifact-id-not-set";
	}
	
	public static List<MavenModule> getModules (Template template) {
		// The current implementation imposes to masterpom template as the last generated artifact)
		List<MavenModule> list = new ArrayList<MavenModule>();
		String s =template.getOutputdir();
		File dir = new File(s);
		FileFilter fileFilter = new FileFilter() {
		    public boolean accept(File file) {
		        return file.isDirectory() && directoryContainsPom(file);
		    }

			private boolean directoryContainsPom(File file) {
				try {
					File pom = new File(file.getCanonicalPath()+"/"+"pom.xml");
					return pom.isFile();
				} catch (IOException e) {
					return false;
				}
			}
		};

		for (File child : dir.listFiles(fileFilter)) {
			list.add(new MavenModule(child.getName()));
		}
		return list;
	}
	
    public static String getOutputDirForJavaOrGroovy (Template template, GeneratorBean bean) {
		if (template.getPropertyValue("groovify", false))
			return "src/main/groovy";		
		return "src/main/java";
    }

	public static String getRootPackage(Template template, GeneratorQualifier bean) {
		return getRootPackage(template, bean.getPackageRoot());
	}	

	
	public static String getRootPackage(Template template, String value) {
		String packageRoot = value;
		if (packageRoot==null)
			return template.getTemplateTarget().getPackageRoot();
		return packageRoot;
	}
	
	public static Driver getDriver (Model model) {
		return model.getDataModel().getDriver();
	}

	public static Boolean hasDriver (Model model) {
		return getDriver(model)!=null;
	}
	
	public static String getModelVersion (Model model) {
		return model.getVersion();
	}
	
	public static String getDbApiGroupId (GeneratorBean bean) {
		return bean.getName()+"BackEnd";
	}
	
	public static String getDbApiArtifactId (GeneratorBean bean) {
		return getDbApiGroupId(bean);
	}
	
	public static String getVersion (Model model) {
		return getModelVersion(model);
	}
	
	public static String getVersion (Application application) {
		final String version = application.getVersion();
		return version==null?"1.0":version;
	}
	
	public static String getDbApiName (GeneratorBean bean) {
		return getDbApiArtifactId(bean);
	}
	
	public static List<MavenDependency> getDependencies (Template template) {
		 Property propertyByName = template.getPropertyByName("maven-dependencies");
		 if (propertyByName!=null) {
			List<Property> dependencies = propertyByName.getProperties();
			return getDependency(dependencies);
		 }
		 return new ArrayList<>();
	}

	public static List<MavenDependency> getDependency(List<Property> dependencies) {
		List<MavenDependency> mList = new ArrayList<MavenDependency>();
		for (Property property : dependencies) {
			mList.add(getDependency(property));
		}
		return mList;
	}
	
	public static MavenDependency getDependency(Property dependency) {
		return getDependency(dependency.getValue());
	}
	
	public static MavenDependency getDependency(String stringFmtDep) {
		return new MavenDependency(stringFmtDep);
	}
}
