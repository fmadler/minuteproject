package net.sf.minuteProject.utils.property;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.code.RestrictedCodeUtils;

public class PropertyUtils {

	private static final String START_WITH = "start-with";
	private static final String END_WITH = "end-with";
	private static final String CONTAIN = "contain";

	public static String getConstraintPropertyValue(Property property) {
		return RestrictedCodeUtils.convertToValidJavaWithUpperCase(property);
	}

	public static String getPropertyValue(Property property) {
		return (property.getValue() != null) ? property.getValue() : property
				.getName();
	}

	public static boolean isPropertyCondition(Property property, String valueToAssess) {
		String name = property.getName();
		String condition = property.getValue();
		if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(condition) && !StringUtils.isEmpty(valueToAssess)) {
			String conditionUpperCase = condition.toUpperCase();
			String valueToAssessUpperCase = valueToAssess.toUpperCase();
			if (START_WITH.equals(name))
				return valueToAssessUpperCase.startsWith(conditionUpperCase);
			if (END_WITH.equals(name))
				return valueToAssessUpperCase.startsWith(conditionUpperCase);
			if (CONTAIN.equals(name))
				return valueToAssessUpperCase.contains(conditionUpperCase);
		}
		return false;
	}
	
	public static boolean isPropertyTagCondition(Property property,
			String name, String condition) {
		String tag = property.getTag();
		name = name.toUpperCase();
		if (!StringUtils.isEmpty(tag)) {
			if (START_WITH.equals(condition))
				return tag.toUpperCase().startsWith(name);
			if (END_WITH.equals(condition))
				return tag.toUpperCase().startsWith(name);
			if (CONTAIN.equals(condition))
				return tag.toUpperCase().contains(name);
		}
		return false;
	}

	public static boolean isPropertyTagStartWith(Property property, String name) {
		return isPropertyTagCondition(property, name, START_WITH);
	}
	
	public static boolean isPropertyTagContain(Property property, String name) {
		return isPropertyTagCondition(property, name, CONTAIN);
	}

	public static String convertValueIfSystemOrEnvironmentVariable(String input) {
		Optional<String> variable = getVariable(input);
		if (variable.isPresent()) {
			return resolveSystemOrEnvironmentVariable(variable.get());
		}
		return input;
	}

	private static String resolveSystemOrEnvironmentVariable(String input) {
		String var = System.getProperty(input);
		if (var!=null)
			return var;
		var = System.getenv(input);
		if (var!=null)
			return var;		
		return input;
	}

	private static Optional<String> getVariable(String input) {
		// TODO Auto-generated method stub
		if (StringUtils.startsWith(input, "${") && StringUtils.endsWith(input, "}")) {
			return Optional.of(StringUtils.substringBetween(input, "${", "}"));
		}
		return Optional.empty();
	}



}
