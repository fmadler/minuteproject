package net.sf.minuteProject.configuration.bean.enrichment.convention;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.View;

import java.util.List;

@SuppressWarnings("serial")
@NoArgsConstructor
@Log4j
public class EntitySearchableConvention extends FieldConvention {

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

	private void apply(Table table) {
		for (Column column : table.getColumns()) {
			if (match(column)) {
				column.setSearchable(true);
			}
		}
	}
	
}
