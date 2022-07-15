package net.sf.minuteProject.configuration.bean.enrichment.convention;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;

@Log4j
@Getter
@Setter
public class DefaultValueConvention extends FieldConvention{
	
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
/*				if (ColumnUtils.isNumeric(column) && StringUtils.isNumeric(defaultValue)) {
					column.setDefaultValue(defaultValue);
				} else {*/
					column.setDefaultValue(defaultValue);
				//}
			}
		}
	}
	
	protected boolean isValid() {
		return hasFieldType() && hasFieldPatternType() && hasFieldPattern();
	}

}
