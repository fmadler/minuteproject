package net.sf.minuteProject.configuration.bean.enrichment.convention;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;

@Log4j
@Getter
@Setter
public class FieldAttributeConvention extends FieldConvention {

	Field field;
	@Override
	public void apply(BusinessModel model) {
		if (isValid()) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		} else
			log.error("FieldValidationConvention not valid");
	}

	public void apply(Table table) {
		for (Column column : table.getColumns()) {
			if (match(column)) {
				BusinessModel.convertFieldAttributeToColumn(field, column);
			}
		}
	}
	
	protected boolean isValid() {
		return super.isValid() && field!=null;
	}

}
