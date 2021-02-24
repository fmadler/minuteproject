package net.sf.minuteProject.configuration.bean.enrichment.convention;

import org.apache.log4j.Logger;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.Stereotype;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;

public class StereotypeConvention extends FieldConvention{

	String stereotype;
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
			logger.error("StereotypeConvention not valid");
	}

	public void apply(Table table) {
		for (Column column : table.getColumns()) {
			if (match(column)) {
				logger.debug("applying stereotype "+stereotype+" to column "+column.getName());
				String stereotypeToLowerCase = stereotype.toLowerCase();
				if (stereotypeToLowerCase.equals("money") || stereotypeToLowerCase.equals("currency") ) {
					if (ColumnUtils.isNumeric(column)) {
						Stereotype s = new Stereotype();
						s.setStereotype(stereotype);
						column.setStereotype(s);
					}
				} else {
					Stereotype s = new Stereotype();
					s.setStereotype(stereotype);
					column.setStereotype(s);
				}
			}
		}
	}
	
	private boolean hasStereotype() {
		return stereotype!=null;
	}
	
	protected boolean isValid() {
		return hasStereotype() && (hasFieldType() || (hasFieldPatternType() && hasFieldPattern()));
	}
	

}
