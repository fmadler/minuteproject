package net.sf.minuteProject.model.data.criteria.type;

import java.util.Date;

import net.sf.minuteProject.model.data.criteria.RangeCriteria;
import net.sf.minuteProject.model.data.criteria.collector.WhereFieldCollector;

public class DateCriteria extends RangeCriteria <Date> {

//	public DateCriteria () {
//		super(new Date());
//	}
	
	public DateCriteria (WhereFieldCollector wfc) {
		super(wfc);
	}
	
}
