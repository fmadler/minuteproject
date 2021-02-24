package net.sf.minuteProject.model.data.criteria.collector;

import java.util.Enumeration;
import java.util.Iterator;

import net.sf.minuteProject.model.data.criteria.AbstractDomainObjectWhat;
import net.sf.minuteProject.model.data.criteria.AbstractDomainObjectWhere;
import net.sf.minuteProject.model.data.criteria.holder.WhatHolder;
import net.sf.minuteProject.model.data.criteria.holder.WhereHolder;

public class WhatDomainObjectCollector extends WhatCollector<AbstractDomainObjectWhat>{

	private WhatFieldCollector wfc;
	
	public WhatDomainObjectCollector (String entityPath) {
		wfc = new WhatFieldCollector();
		wfc.setEntityPath(entityPath);
	}

	public WhatFieldCollector setWfc(String field) {
		wfc.setField(field);
		return wfc;
	}

	public WhatFieldCollector getWfc(String field) {
		return wfc;
	}
	
	public String popToString () {
		StringBuffer sb = new StringBuffer();
		Enumeration <WhatHolder> e = wfc.getElements();
		while (e.hasMoreElements()) {
			WhatHolder whatHolder = (WhatHolder) e.nextElement();
			sb.append(whatHolder);
			sb.append("\n");
		}
		return sb.toString();
	}
}
