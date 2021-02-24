package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ViewDDLUtils;
import net.sf.minuteProject.utils.ColumnUtils;

@SuppressWarnings("serial")
public class ViewDefaultPrimaryKeyConvention extends TableDefaultPrimaryKeyConvention {

	@Override
	public void apply(BusinessModel model) {
		if (model.getBusinessPackage()!=null) {
			for (View view : model.getBusinessPackage().getViews()) {
				if (view.getPrimaryKeyColumns().length==0)
					apply (view);
			}
		}
	}
	private void apply(View view) {
		view.setVirtualPrimaryKeys(getVirtualPrimaryKey(view));
	}

	
}
