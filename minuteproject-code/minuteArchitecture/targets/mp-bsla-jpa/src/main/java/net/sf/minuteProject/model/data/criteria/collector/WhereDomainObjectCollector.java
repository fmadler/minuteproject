package net.sf.minuteProject.model.data.criteria.collector;

import java.util.Enumeration;
import java.util.Iterator;

import net.sf.minuteProject.model.data.criteria.AbstractDomainObjectWhere;
import net.sf.minuteProject.model.data.criteria.holder.WhereHolder;

public class WhereDomainObjectCollector extends WhereCollector<AbstractDomainObjectWhere>{

	private WhereFieldCollector wfc;
	
	public WhereDomainObjectCollector (String entityPath) {
		wfc = new WhereFieldCollector();
		wfc.setEntityPath(entityPath);
	}

	public WhereFieldCollector getWfc(String field) {
		wfc.setField(field);
		return wfc;
	}

//	public void setWfc(WhereFieldCollector wfc) {
//		this.wfc = wfc;
//	}
//	
	public String popToString () {
		StringBuffer sb = new StringBuffer();
		Enumeration <WhereHolder> e = wfc.getElements();
		while (e.hasMoreElements()) {
			WhereHolder whereHolder = (WhereHolder) e.nextElement();
			sb.append(whereHolder);
			sb.append("\n");
		}
		return sb.toString();
	}
}
