package net.sf.minuteproject.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ParserUtils {

	public static List<String> getDistinct(String s) {
		Set<String> set = new LinkedHashSet<String>(getList(s));
		List<String> list = new ArrayList<String>();
		list.addAll(set);
		return list;
	}

	public static List<String> getListLowerCase(String s) {
		if (s != null)
			return getList(s.toLowerCase());
		return new ArrayList<String>();
	}

	public static List<String> getList(String s) {
		List<String> l = new ArrayList<String>();
		if (s != null) {
			s = StringUtils.replace(s, " ", "");
			return Arrays.asList(StringUtils.split(s, ","));
		}
		return l;
	}

	public static boolean isInList(String name, String list) {
		if (list == null || list.isEmpty())
			return false;
		return getList(list).contains(name);
	}

	public static String getQuotedElementListAsString(String s) {
		StringBuffer sb = new StringBuffer();
		List<String> list = getList(s);
		int size = list.size();
		for (int i = 0; i < size; i++) {
			sb.append("'"+list.get(i)+"'");
			if (i<size-1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
