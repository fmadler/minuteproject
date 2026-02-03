package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;

public class ExtensionUtils {

	public String getGroovyNature(Template template, GeneratorBean bean) {
		if (template.hasProperty("groovify"))
			return "groovy";
		return null;
	}
	
	public String getExtensionJavaOrGroovy (Template template, GeneratorBean bean) {
		if (template.getPropertyValue("groovify", false))
			return "groovy";		
		return "java";
	}
}
