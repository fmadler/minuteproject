package net.sf.minuteProject.plugin.environment;

import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.environment.Environment;

public class EnvironmentUtils {

	public static String getEnvironmentName(Template template, GeneratorBean bean) {
		if (bean instanceof Environment) {
			Environment environment = (Environment)bean;
			String s = environment.getEnvironments().getPropertyPlaceholder();
			
		}
		return bean.getName()==null?"environment":"environment-"+bean.getName().toLowerCase();
	}	
	
	public static boolean isRemoteAndEnvironmentConfigInDb(Configuration configuration) {
		//##TODO_ENV
		return (configuration.hasPropertyValue("target","remote") 
				&& hasDbEnvironment(configuration)
				) ;
	}

	private static boolean hasDbEnvironment(Configuration configuration) {
		return configuration.getEnvironments()!=null 
			&& configuration.getEnvironments().getConfigQueryId()!=null
			&& configuration.getEnvironments().getConfigQueryResultKey()!=null
			&& configuration.getEnvironments().getConfigQueryResultValue()!=null
		;
	}
}
