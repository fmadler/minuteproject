package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.collector.WhatDomainObjectCollector;
import net.sf.minuteProject.model.data.criteria.collector.WhatFieldCollector;

public class AbstractDomainObjectWhat {

	public static final String COUNT 		= "COUNT";
	
	protected WhatDomainObjectCollector wdoc;
	
	private String beanPath;
	
	public String getBeanPath() {
		return beanPath;
	}

	public void setBeanPath(String beanPath) {
		this.beanPath = beanPath;
	}

	public WhatDomainObjectCollector getWdoc() {
		return wdoc;
	}

	public void setWdoc(WhatDomainObjectCollector wdoc) {
		this.wdoc = wdoc;
	}

	public AbstractDomainObjectWhat (String beanPath) {
		wdoc = new WhatDomainObjectCollector(beanPath);
	}
	
	public String popWhatCriteria () {
		return wdoc.popToString();
	}
	
	public WhatFieldCollector count () {
		return addElement(COUNT);
	}
	
	protected final WhatFieldCollector addElement (String function) {
		wdoc.setWfc(getBeanPath()).addElement(function);
		return wdoc.setWfc(getBeanPath());
	}
}
