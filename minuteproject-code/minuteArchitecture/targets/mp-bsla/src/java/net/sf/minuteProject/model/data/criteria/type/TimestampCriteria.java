package net.sf.minuteProject.model.data.criteria.type;

import java.sql.Timestamp;
import java.util.Date;

import net.sf.minuteProject.model.data.criteria.ComparisonCriteria;
import net.sf.minuteProject.model.data.criteria.collector.WhereFieldCollector;

public class TimestampCriteria extends ComparisonCriteria<Timestamp> {

	public TimestampCriteria (WhereFieldCollector wfc) {
		super (wfc);
	}
}
