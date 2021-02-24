package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.StringUtils;

public abstract class ModelColumnConvention extends ModelConvention {

	protected String fieldPattern, fieldPatternType, fieldContentType;
	
	@Override
	public void apply(BusinessModel model) {
		if (isValid()) {
			for (Table table : model.getBusinessPackage().getEntities()) {
				apply(table);
			}
		}
	}

	protected abstract boolean isValid() ;

	protected boolean isValidStrict () {
		return fieldContentType!=null && fieldPattern!=null && fieldPatternType!=null;
	}
	
	public void apply(Table table) {
		for (Column column: table.getColumns()) {
			apply(column);
		}
	}
	
	public void apply(Column column) {
		if (match(column)) {
			performConvention (column);
		}
	}
	
	protected abstract void performConvention(Column column) ;

	public boolean match (Column column) {
		if (fieldContentType!=null) {
			String umlType = ConvertUtils.getUMLTypeFromDBFullType(column.getType());
			if (!umlType.toLowerCase().equals(fieldContentType.toLowerCase()))
				return false;
		}
		return StringUtils.checkExpression(fieldPattern, fieldPatternType, column.getName());
	}

	public String getFieldPattern() {
		return fieldPattern;
	}

	public void setFieldPattern(String fieldPattern) {
		this.fieldPattern = fieldPattern;
	}

	public String getFieldPatternType() {
		return fieldPatternType;
	}

	public void setFieldPatternType(String fieldPatternType) {
		this.fieldPatternType = fieldPatternType;
	}

	public String getFieldContentType() {
		return fieldContentType;
	}

	public void setFieldContentType(String fieldContentType) {
		this.fieldContentType = fieldContentType;
	}
	
	
}
