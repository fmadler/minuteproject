package net.sf.minuteProject.plugin.readme;

import net.sf.minuteProject.configuration.bean.Template;

public class ReadmeUtils {

	public String getReadme(Template template) {
		String propertyValue = template.getPropertyValue("readme");
		return (propertyValue!=null) ? propertyValue.replaceAll("\\\\", "\\n"): "please add a readme property!";
	}
	
}
