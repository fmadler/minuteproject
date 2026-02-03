package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.Configuration;

public abstract class Convention<T> extends AbstractConfiguration {

	public String type;
	public String defaultValue;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}	
		
	public abstract void apply(T t) ;
}
