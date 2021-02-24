package net.sf.minuteProject.configuration.bean.enumeration;

import java.util.Arrays;

public enum Scope {

	//EntityScope
	BACKEND, ALL_STACKS;
	
	public static Scope fromValue(String scope) {
		return Arrays.asList(Scope.values()).stream().filter(u -> u.name().equalsIgnoreCase(scope)).findFirst().orElse(null);
	}
 }
