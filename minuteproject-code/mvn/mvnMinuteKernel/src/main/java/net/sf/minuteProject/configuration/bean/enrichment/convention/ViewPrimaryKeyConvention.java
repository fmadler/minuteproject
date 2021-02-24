package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ViewDDLUtils;
import net.sf.minuteProject.utils.ColumnUtils;

@SuppressWarnings("serial")
public class ViewPrimaryKeyConvention extends PrimaryKeyConvention<View> {

	public ViewPrimaryKeyConvention(){}
	@Override
	protected List<View> getEntity(BusinessModel model) {
//		List<Table> list = new ArrayList<Table>();
//		for (View view : model.getBusinessPackage().getViews()) {
//			list.add((Table)view);
//		}
		return model.getBusinessPackage().getViews();
	}

	protected void applyDefaultPkConvention(View view) {
		Column[] virtualPrimaryKey = getVirtualPrimaryKey(view);
		if (virtualPrimaryKey.length>0) {
			view.addVirtualPrimaryKey(virtualPrimaryKey[0]);
		}
	}
	
//	protected void applyDefaultPkConvention(View view) {
//		Column[] primaryKeyFirstFieldAndOtherMatching = getPrimaryKeyFirstFieldAndOtherMatching(view);
//		for (Column column : primaryKeyFirstFieldAndOtherMatching) {
//			view.addVirtualPrimaryKey(column);
//		}
//	}
	protected void applyPkOnFirstFieldAndOtherMatching(View view) {
		Column[] primaryKeyFirstFieldAndOtherMatching = getPrimaryKeyFirstFieldAndOtherMatching(view);
		for (Column column : primaryKeyFirstFieldAndOtherMatching) {
			view.addVirtualPrimaryKey(column);
		}
	}
	
}
