package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.List;

public class ActionColumnMapEntry {

	private String columnField;
	private String convertCase;
	private List<ActionFieldMapEntry> actionFieldMapEntries;
	
	public List<ActionFieldMapEntry> getActionFieldMapEntries() {
		if (actionFieldMapEntries == null) actionFieldMapEntries = new ArrayList<ActionFieldMapEntry>();
		return actionFieldMapEntries;
	}
	
	public void addActionFieldMapEntry(ActionFieldMapEntry actionFieldMapEntry) {
		getActionFieldMapEntries().add(actionFieldMapEntry);
	}
	
	public String getConvertCase() {
		return convertCase;
	}

	public void setConvertCase(String convertCase) {
		this.convertCase = convertCase;
	}
	
	public boolean isUpperCaseConvert () {
		return "uppercase".equals(convertCase);
	}

	public String getColumnField() {
		return columnField;
	}

	public void setColumnField(String columnField) {
		this.columnField = columnField;
	}
	
}
