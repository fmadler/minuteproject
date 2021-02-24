package net.sf.minuteProject.plugin.kendoui

import net.sf.minuteProject.configuration.bean.Template;

class KendoUiUtils {

	public String pageSize() {
		30;
	}
	
	public int websiteContextIndex(Template template) {
		template.hasPropertyValue("cloud-platform", false)?0:2;
	}
}
