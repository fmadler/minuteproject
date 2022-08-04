package net.sf.minuteProject.utils.criteria;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.enumeration.Order;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.query.Ordering;
import net.sf.minuteProject.utils.TableUtils;

public class OrderingUtils {

	private Logger logger = Logger.getLogger(OrderingUtils.class);
	public Ordering getFirstOrdering(Table table) {
		//1 get ordering enrichment
		List<Ordering> orderings = table.getOrderings();
		if (orderings.size()>0) {
			return orderings.get(0);
		}
		//otherwise
		//2 get semantic reference first column
		Ordering ordering = new Ordering();
		ordering.setOrder(Order.ASC);
		List<Column> sr = TableUtils.getSemanticReferenceColumns(table);
		if (sr.size()>0) {
			ordering.setColumn(sr.get(0));
			return ordering;
		}
		//3 get primary key
		Column pk = TableUtils.getPrimaryFirstColumn(table);
		if(pk!=null) {
			ordering.setColumn(pk);
			return ordering;
		}
		//4 to prevent pk not set
		logger.warn("TABLE "+table.getName()+ " should have a PK, please apply Primary-key conventions if PK is not physically enabled");
		pk = table.getColumn(0);
		if(pk!=null) {
			ordering.setColumn(pk);
			return ordering;
		}
		
		return null;
	}

}
