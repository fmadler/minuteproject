package net.sf.minuteProject.utils.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class ParserUtils {

	public static List<String> getDistinct(String s) {
		Set<String> set = new LinkedHashSet<String>(getList(s));
		List<String> list = new ArrayList<String>();
		list.addAll(set);
		return list;
	}
	
	public static List<String> getListLowerCase(String s) {
		if (s!=null)
			return getList(s.toLowerCase());
		return new ArrayList<String>();
	}
	
	public static List<String> getList(String s) {
		List<String> l = new ArrayList<String>();
		if (s!=null) {
			s = StringUtils.replace(s, " ", "");
			return getListFromCommaSeparated(s);
		}
		return l;
	}
	
	public static List<String> getListFromCommaSeparated(String s) {
		return Arrays.asList(StringUtils.split(s, ","));
	}

	public static boolean isInList(String name,
			String list) {
		if (list==null || list.isEmpty()) 
			return false;
		return getList(list).contains(name);
	}
	
	public static boolean isInListLowerCase(String name,
			String list) {
		if (list==null || list.isEmpty() || name == null) 
			return false;
		return getListLowerCase(list).contains(name.toLowerCase());
	}
	
	public static String getProperty(String input) {
		int i = StringUtils.indexOf(input, "${");
		int j = StringUtils.indexOf(input, "}");
		return StringUtils.substring(input, i+2, j);
	}

}
