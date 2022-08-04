package net.sf.minuteProject.plugin.ember;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.FormatUtils;

public class EmberUtils {

	public static String getEmberFileName(Template template, GeneratorBean bean) {
		if (bean.getName()!=null) {
			StringBuilder sb = new StringBuilder();
			if (!StringUtils.isEmpty(template.getFilePrefix())) {
				sb.append(template.getFilePrefix()+"_");
			}
			sb.append(bean.getName());
			if (!StringUtils.isEmpty(template.getFileSuffix())) {
				sb.append("_"+template.getFileSuffix());
			}
			return sb.toString().replaceAll("_", "-").toLowerCase();
		}
		return "name not converted";
	}
	
	public static String getEmberClass(String name) {
		return FormatUtils.getJavaName(name.replaceAll("-", "_"));
	}
	
	
	public static String getEmberClassVariable(String name) {
		return FormatUtils.getJavaNameVariable(name.replaceAll("-", "_"));
	}

	public static Stream<String> getComponentStream(String input) {
		return splitByDot(input).stream()
			.filter(u -> !u.equals(""))
			.map(
					u-> {
						return Arrays.asList(u.split("-")).stream()
								.map(v ->  {
									return v.substring(0, 1).toUpperCase() + v.substring(1);
									})
								.collect(Collectors.joining());
								
					}
					)
			;
	}
	
	public static Stream<String> getComponentRouterStream(String input) {
		return splitByDot(input).stream()
			.filter(u -> !u.equals(""))
			.map(
					u-> {
						String s = Arrays.asList(u.split("-")).stream()
								.map(v ->  {
									return v.substring(0, 1).toUpperCase() + v.substring(1);
									})
								.collect(Collectors.joining())
								;
						return s.substring(0, 1).toLowerCase() + s.substring(1);
								
					}
				)
			;
	}
	
	public static String getComponentPath(String input) {
		return getComponentStream(input)
			.collect(Collectors.joining("::"));
	}

	public static String getComponentClassPath(String input) {
		return getComponentStream(input)
			.collect(Collectors.joining(""));
	}

	public static String getComponentRouterPath(String input) {
		return getComponentRouterStream(input)
			.collect(Collectors.joining("."));
	}
	
	static List<String> splitByDot(String input) {
		return Arrays.asList(input.split("\\."));
	}
	
	public static String getComponentPath(AbstractConfiguration bean, Template template, String targetTemplateName) {
		String input = CommonUtils.getLevelTemplateFullClassPath(bean, template, targetTemplateName);
		return getComponentPath(input);
	}
	
	public static String strip (String input, String remove) {
		return StringUtils.removeStart(input, remove);
	}

}
