package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.DomainObjectCriteria;
import net.sf.minuteProject.model.data.criteria.collector.WhereCollector;
import net.sf.minuteProject.model.data.criteria.collector.WhereDomainObjectCollector;
import net.sf.minuteProject.model.data.criteria.collector.WhereFieldCollector;

public class AbstractDomainObjectWhere {

	protected WhereDomainObjectCollector wdoc;
	
	private String beanPath;
	
	public String getBeanPath() {
		return beanPath;
	}

	public void setBeanPath(String beanPath) {
		this.beanPath = beanPath;
	}

	public WhereDomainObjectCollector getWdoc() {
		return wdoc;
	}

	public void setWdoc(WhereDomainObjectCollector wdoc) {
		this.wdoc = wdoc;
	}

	public AbstractDomainObjectWhere (String beanPath) {
		wdoc = new WhereDomainObjectCollector(beanPath);
	}
	
	public String popWhereCriteria () {
		return wdoc.popToString();
	}
	
	public void or (WhereFieldCollector wfc) {
		// disregard if or is the first condition
		wfc.addElement("OR");
	}

	public void and (WhereFieldCollector wfc) {
		wfc.addElement("AND");
	}
	
}
