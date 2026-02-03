package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.rule.ActionCondition;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Rule;
import net.sf.minuteProject.configuration.bean.enumeration.CRUDEnum;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.FormatUtils;

public class Action extends Rule<Table> {

	private String defaultImplementation;
	private List<Field> fields;
	private List<ActionFieldMapEntry> actionFieldMapEntries;
	private List<ActionColumnMapEntry> actionColumnMapEntries;
	private ActionCondition actionCondition;
	private String queryId;
	
	public String getTechnicalPackage(Template template) {
		if (getParent()!=null)
			return getParent().getPackage().getTechnicalPackage(template)+"."+StringUtils.lowerCase(FormatUtils.getJavaName(getParent().getName()));
		return super.getTechnicalPackage(template);
	}

	public String getDefaultImplementation() {
		return defaultImplementation;
	}

	public void setDefaultImplementation(String defaultImplementation) {
		this.defaultImplementation = defaultImplementation;
	}

	public List<ActionColumnMapEntry> getActionColumnMapEntries() {
		if (actionColumnMapEntries == null) actionColumnMapEntries = new ArrayList<ActionColumnMapEntry>();
		return actionColumnMapEntries;
	}
	
	public void addActionColumnMapEntry(ActionColumnMapEntry actionColumnMapEntry) {
		getActionColumnMapEntries().add(actionColumnMapEntry);
	}
	
	public List<ActionFieldMapEntry> getActionFieldMapEntries() {
		if (actionFieldMapEntries == null) actionFieldMapEntries = new ArrayList<ActionFieldMapEntry>();
		return actionFieldMapEntries;
	}
	
	public void addActionFieldMapEntry(ActionFieldMapEntry actionFieldMapEntry) {
		getActionFieldMapEntries().add(actionFieldMapEntry);
	}
	
	public List<Field> getFields() {
		if (fields == null) fields = new ArrayList<Field>();
		return fields;
	}
	
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public void addField(Field field) {
		getFields().add(field);
	}
	
	public ActionCondition getActionCondition() {
		return actionCondition;
	}

	public void setActionCondition(ActionCondition actionCondition) {
		this.actionCondition = actionCondition;
	}

	public boolean hasDefaultImplementation (String impl) {
		return net.sf.minuteProject.utils.StringUtils.equalsIgnoreCase(defaultImplementation, impl);
	}
	
	public boolean hasDefaultImplementation () {
		return CRUDEnum.isInCRUDEnum(defaultImplementation);
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	
	public List<ActionFieldMapEntry> getPopupFields() {
		return getActionFieldMapEntries().stream()
				.filter(a -> StringUtils.isNotEmpty(a.getRequestedField()) || StringUtils.isNotEmpty(a.getInputField()))
				.collect(Collectors.toList());
	}
}
