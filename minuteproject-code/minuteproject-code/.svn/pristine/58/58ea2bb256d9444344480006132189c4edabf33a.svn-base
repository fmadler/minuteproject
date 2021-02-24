package net.sf.minuteProject.utils.security;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.enrichment.security.EntitySecuredAccess;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class EntitySecurityUtils {

	public static boolean isEntityRoleSecured(EntitySecuredAccess esa) {
		return (esa!=null && esa.getRoles()!=null)?true:false;
	}
	
	public static String getEntitySecuredRoles(EntitySecuredAccess esa) {
		return (esa!=null)?esa.getRoles():null;
	}	

	public static boolean isEntityRoleSecured(net.sf.minuteProject.configuration.bean.Package pack) {
		//TODO adapt when security on entities
		return isEntityRoleSecured(pack.getSecurityColor());
	}
	
	public static String getEntitySecuredRoles(net.sf.minuteProject.configuration.bean.Package pack) {
		return getEntitySecuredRoles(pack.getSecurityColor());
	}	
	
	public static boolean isEntityRoleSecured(Table table) {
		return isEntityRoleSecured(table.getEntitySecuredAccess());
	}
	
	public static String getEntitySecuredRoles(Table table) {
		return getEntitySecuredRoles(table.getEntitySecuredAccess());
	}	
	
	public List<String> getDistinctRoles(Model model){
		StringBuffer rolesAsString = new StringBuffer();
		for (net.sf.minuteProject.configuration.bean.Package pack : model.getBusinessModel().getBusinessPackage().getPackages()) {

			rolesAsString.append(getEntitySecuredRoles(pack));
		}
		return ParserUtils.getDistinct(rolesAsString.toString());
	}
}
