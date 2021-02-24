package net.sf.minuteProject.configuration.bean.enrichment.convention

import org.apache.log4j.Logger;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.enrichment.Stereotype;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.StringUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

class StereotypeConvention extends FieldConvention{

	String stereotype
	Boolean override=true
	private Logger logger = Logger.getLogger(StereotypeConvention.class);
	
	@Override
	public void apply(BusinessModel model) {
		if (isValid()) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		} else
			logger.error("StereotypeConvention not valid")
	}

	public void apply(Table table) {
		for (Column column : table.getColumns()) {
			if (match(column)) {
				logger.debug("applying stereotype "+stereotype+" to column "+column.getName())
				column.setStereotype(new Stereotype(stereotype: stereotype))
			}
		}
	}
	
	private boolean hasStereotype() {
		stereotype!=null
	}
	
	protected boolean isValid() {
		hasStereotype() && (hasFieldType() || (hasFieldPatternType() && hasFieldPattern())) ;
	}
	

}
