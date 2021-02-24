package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenerationCondition extends GenerationConditionAdapter {
	
	private boolean excludeTables=false, excludeViews=false;

	public boolean isExcludeTables() {
		return excludeTables;
	}

	public void setExcludeTables(boolean excludeTables) {
		this.excludeTables = excludeTables;
	}

	public boolean isExcludeViews() {
		return excludeViews;
	}

	public void setExcludeViews(boolean excludeViews) {
		this.excludeViews = excludeViews;
	}


}
