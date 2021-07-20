package net.sf.minuteProject.configuration.bean.enrichment.convention;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import net.sf.minuteProject.configuration.bean.enumeration.Order;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.query.Ordering;

@Data
@Log4j
public class OrderingConvention extends StereotypeConvention{

	String ordering;

	protected boolean isValid() {
		return hasOrdering();
	}
	
	private boolean hasOrdering() {
		return ordering!=null;
	}
	
	public void apply (Table table) {
		for (Column column : table.getColumns()) {
			if (match(column)) {
				Ordering ordering2 = new Ordering();
				ordering2.setColumn (column);
				ordering2.setOrder(Order.getOrder(ordering));
				table.addOrdering(ordering2);
			}
		}
	}

	@Override
	public String errorMessage() {
		return "OrderingConvention not valid" + this;
	}

}
