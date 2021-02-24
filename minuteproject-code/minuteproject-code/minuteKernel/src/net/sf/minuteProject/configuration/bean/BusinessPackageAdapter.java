package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.group.Group;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.TableUtils;

public abstract class BusinessPackageAdapter extends AbstractConfiguration {

	protected String defaultPackage, defaultPackageType, autoPackageType;

	protected List<Condition> conditions;
	
	public void addCondition(Condition condition) {
		if (conditions == null)
			conditions = new ArrayList<Condition>();
		conditions.add(condition);
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}
	
	public String getPackage(GeneratorBean bean) {
		return getConditionsResult(bean);
	}

	private String getConditionsResult(GeneratorBean bean) {
		if (conditions!=null) {
			for (Condition condition : conditions) {
				if (condition.getConditionResult(bean) != null) {
					return condition.getResult();
				}
			}
		}
		return getDefaultPackage();
	}

	protected abstract String getDefaultPackage();
	
	public void setDefaultPackage(String defaultPackage) {
		this.defaultPackage = defaultPackage;
	}

	public String getAutoPackageType() {
		return autoPackageType;
	}

	public void setAutoPackageType(String autoPackageType) {
		this.autoPackageType = autoPackageType;
	}

	public String getDefaultPackageType() {
		return defaultPackageType;
	}

	public void setDefaultPackageType(String defaultPackageType) {
		this.defaultPackageType = defaultPackageType;
	}
	

}
