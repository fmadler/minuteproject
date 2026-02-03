package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.List;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;

public class TablePrimaryKeyConvention extends PrimaryKeyConvention<Table> {

	public TablePrimaryKeyConvention(){}
	@Override
	protected List<Table> getEntity(BusinessModel model) {
		return model.getBusinessPackage().getTables();
	}

}
