package net.sf.minuteProject.configuration.bean.enrichment.convention

import org.apache.log4j.Logger

import net.sf.minuteProject.configuration.bean.BusinessModel
import net.sf.minuteProject.configuration.bean.enrichment.convention.ModelConvention
import net.sf.minuteProject.configuration.bean.enrichment.convention.StereotypeConvention;
import net.sf.minuteProject.configuration.bean.enumeration.Order;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table
import net.sf.minuteProject.configuration.bean.query.Ordering;

class OrderingConvention extends StereotypeConvention{

	String ordering
	
	private Logger logger = Logger.getLogger(OrderingConvention.class)

	protected boolean isValid() {
		hasOrdering()
	}
	
	private boolean hasOrdering() {
		ordering!=null
	}
	
	public void apply (Table table) {
		for (Column column : table.getColumns()) {
			if (match(column)) {
				Ordering ordering2 = new Ordering();
				ordering2.setColumn (column)
				ordering2.setOrder(Order.getOrder(ordering))
				table.addOrdering(ordering2)
			}
		}
	}
	
	
	
}
