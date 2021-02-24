package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.StringUtils;

public class ColumnDefaultValueConvention extends ModelConvention {

	private String pattern;
	
	@Override
	public void apply(BusinessModel model) {
		if (isValid()) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		}
	}

	private void apply(Table table) {
		for (Column column : table.getColumns()) {
			if (match(column))
				column.setDefaultValue(defaultValue);
		}
	}

	private boolean match(Column column) {
		return StringUtils.checkExpression(column.getName(), type, pattern);
	}

	private boolean isValid() {
		if (pattern==null || type==null)
			return false;
		return true;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
