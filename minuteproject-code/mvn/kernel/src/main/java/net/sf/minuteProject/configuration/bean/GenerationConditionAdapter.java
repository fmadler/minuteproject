package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenerationConditionAdapter extends AbstractConfiguration {
	
	public static final String FILTER_FILE_TYPE_EXCLUDE = "exclude";
	public static final String FILTER_FILE_TYPE_INCLUDE = "include";
	
	private String defaultType;
	private List<Condition> conditions;
	
	public void addCondition (Condition condition) {
		if (conditions==null)
			conditions = new ArrayList<Condition>();
		conditions.add(condition);
	}

	public List<Condition> getConditions() {
		if (conditions== null)
			conditions = new ArrayList<Condition>();
		return conditions;
	}
	
	public boolean isAddable (GeneratorBean bean) {
		if (defaultType==null || defaultType.equals(FILTER_FILE_TYPE_INCLUDE))
			return includeOtherwiseExpressInExcludeExpression(bean);
		else
			return excludeOtherwiseExpressInIncludeExpression(bean);		
	}
	
	public boolean areConditionsTrue(GeneratorBean bean) {
		if (defaultType==null || defaultType.equals(FILTER_FILE_TYPE_INCLUDE)) //default set to include
			return includeOtherwiseExpressInExcludeExpression(bean);
		else
			return excludeOtherwiseExpressInIncludeExpression(bean);
	}
	
	public boolean includeOtherwiseExpressInExcludeExpression(GeneratorBean bean) {
		for (Condition condition : getConditions()){
			if (condition.getType().equals(FILTER_FILE_TYPE_EXCLUDE)
				&& condition.isConditionTrue(bean))
					return false;
		}
		return true;
	}
	
	public boolean excludeOtherwiseExpressInIncludeExpression(GeneratorBean bean) {
		for (Condition condition : getConditions()){
			if (condition.getType().equals(FILTER_FILE_TYPE_INCLUDE) 
				&& condition.isConditionTrue(bean))
				return true;
		}
		return false;
	}

	public String getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}
	
}
