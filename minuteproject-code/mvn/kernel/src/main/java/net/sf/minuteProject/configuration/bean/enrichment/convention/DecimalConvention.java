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

public class DecimalConvention extends ModelColumnConvention {

	@Override
	protected boolean isValid() {
		return fieldPattern!=null && fieldPatternType!=null;
	}

	@Override
	protected void performConvention(Column column) {
		//TODO but for OX trace BigDecimal
	}
	
}
