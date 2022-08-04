package net.sf.minuteProject.plugin.kendoui;

import net.sf.minuteProject.configuration.bean.Template;

public class KendoUiUtils {

	public String pageSize() {
		return "30";
	}
	
	public int websiteContextIndex(Template template) {
		return template.hasPropertyValue("cloud-platform", false)?0:2;
	}
}
