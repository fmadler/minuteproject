package net.sf.minuteProject.plugin.server.wls;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import org.apache.commons.lang.StringUtils;

class WeblogicUtils {

	public String getContextRoot(Template template, Model model) {
		String context = template.getPropertyValue("context-root");
		if (StringUtils.isEmpty(context)) {
			context = model.getName()+"App";
		}
		return context;
	}

}
