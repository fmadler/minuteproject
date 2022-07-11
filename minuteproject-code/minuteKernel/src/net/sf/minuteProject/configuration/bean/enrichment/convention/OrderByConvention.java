package net.sf.minuteProject.configuration.bean.enrichment.convention;

import lombok.extern.log4j.Log4j;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.Stereotype;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;

@Log4j
public class OrderByConvention extends FieldConvention {

	String direction;

	@Override
	public void apply(BusinessModel model) {
		if (isValid()) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		} else
			log.error("OrderByConvention not valid");
	}

	public void apply(Table table) {
		for (Column column : table.getColumns()) {
			if (match(column)) {
				log.debug("applying stereotype "+direction+" to column "+column.getName());
				Stereotype stereotype = new Stereotype();
				stereotype.setStereotype(direction);

				//column.setStereotype(new Stereotype(stereotype: direction)) //former groovy
				column.setStereotype(stereotype);
			}
		}
	}
	
	private boolean hasDirection() {
		return direction!=null;
	}
	
	protected boolean isValid() {
		return hasDirection() && (hasFieldType() || (hasFieldPatternType() && hasFieldPattern())) ;
	}
	

}
