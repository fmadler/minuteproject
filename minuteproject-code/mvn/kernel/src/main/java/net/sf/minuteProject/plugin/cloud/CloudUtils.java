package net.sf.minuteProject.plugin.cloud;

import net.sf.minuteProject.configuration.bean.Application;
import net.sf.minuteProject.configuration.bean.GeneratorQualifier;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.plugin.maven.MavenUtils;

public class CloudUtils {
//TODO add to ReleaseUtils
//TODO get the release app from the technology-target
	
	public static String  CLOUD_APPLICATION_NAME = "cloud-application-name";
	public static String  CLOUD_DATABASES_NAME = "cloud-databases-name";
	
	public static String getAppName (Template template) {
		return template.getPropertyValue("cloud-name");
	}
	
	public static String getReleaseFileName (Template template, Model bean) {
		return getReleaseFileNameGeneric(template, bean);
	}
	
	public static String getReleaseFileName (Template template, Application bean) {
		return getReleaseFileNameGeneric(template, bean);
	}
	
	public static String getReleaseFileNameGeneric (Template template, GeneratorQualifier bean) {
		Targets t = template.getTemplateTarget().getTarget().getTargets();
		String finalName = MavenUtils.getFinalName(template, bean);
		String catalogEntry = template.getPropertyValue ("catalog-entry");
		if (catalogEntry!=null) {
			
			if (catalogEntry.equalsIgnoreCase("openxava"))
				return "../dist/"+bean.getName()+".war";
			if (catalogEntry.equalsIgnoreCase("primefaces-spring"))
				return "JSF-Primefaces/target/"+bean.getName()+"App.war";
			if (catalogEntry.equalsIgnoreCase("primefaces-jee"))
				return "JSF-Primefaces/target/"+bean.getName()+"App.war";
			if (catalogEntry.equalsIgnoreCase("rest-kendoui"))
				return "REST/target/"+finalName+".war";
		}
		
		return template.getPropertyValue("release.name");
	}
	

	public String getApplicationName(Template template) {
		return template.getPropertyValue(CLOUD_APPLICATION_NAME);
	}
	
	public String getDatabaseName(Template template) {
		return template.getPropertyValue(CLOUD_DATABASES_NAME);
	}
	
}
