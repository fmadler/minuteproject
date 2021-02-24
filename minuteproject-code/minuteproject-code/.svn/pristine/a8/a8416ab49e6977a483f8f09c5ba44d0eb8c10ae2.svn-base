package net.sf.minuteProject.configuration.bean.enrichment.convention

import org.apache.log4j.Logger;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.enrichment.Stereotype;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.StringUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

class OrderByConvention extends FieldConvention {

	String direction;
	private Logger logger = Logger.getLogger(OrderByConvention.class);
	
	@Override
	public void apply(BusinessModel model) {
		if (isValid()) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		} else
			logger.error("OrderByConvention not valid")
	}

	public void apply(Table table) {
		for (Column column : table.getColumns()) {
			if (match(column)) {
				logger.debug("applying stereotype "+direction+" to column "+column.getName())
				column.setStereotype(new Stereotype(stereotype: direction))
			}
		}
	}
	
	private boolean hasDirection() {
		direction!=null
	}
	
	protected boolean isValid() {
		hasDirection() && (hasFieldType() || (hasFieldPatternType() && hasFieldPattern())) ;
	}
	

}
