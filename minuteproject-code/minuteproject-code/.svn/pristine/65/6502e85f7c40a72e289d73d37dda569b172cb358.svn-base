package net.sf.minuteProject.configuration.bean.enumeration;

import net.sf.minuteProject.utils.StringUtils;

public enum CRUDEnum {
	INSERT, UPDATE, SELECT, DELETE;
	
	public static CRUDEnum getCRUDEnum(String crud) {
		for (CRUDEnum c : values()) {
			if (StringUtils.equalsIgnoreCase(c.name(), crud))
				return c;
		}
		return null;
	}
	
	public static boolean isInCRUDEnum(String crud) {
		return (getCRUDEnum(crud)!=null);
	}
}
