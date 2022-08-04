package net.sf.minuteProject.plugin.persistence;

import net.sf.minuteProject.configuration.bean.Model;

public class PersistenceUtils {

	public static String getDatasourceName(Model model) {
		return "jdbc/"+model.getName()+"DS";
	}
	
}
