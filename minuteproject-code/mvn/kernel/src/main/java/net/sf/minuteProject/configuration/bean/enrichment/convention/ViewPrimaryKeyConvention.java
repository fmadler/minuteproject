package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.List;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.View;

@SuppressWarnings("serial")
public class ViewPrimaryKeyConvention extends PrimaryKeyConvention<View> {

	public ViewPrimaryKeyConvention(){}
	@Override
	protected List<View> getEntity(BusinessModel model) {
		return model.getBusinessPackage().getViews();
	}

	protected void applyDefaultPkConvention(View view) {
		Column[] virtualPrimaryKey = getVirtualPrimaryKey(view);
		if (virtualPrimaryKey.length>0) {
			view.addVirtualPrimaryKey(virtualPrimaryKey[0]);
		}
	}

	protected void applyPkOnFirstFieldAndOtherMatching(View view) {
		Column[] primaryKeyFirstFieldAndOtherMatching = getPrimaryKeyFirstFieldAndOtherMatching(view);
		for (Column column : primaryKeyFirstFieldAndOtherMatching) {
			view.addVirtualPrimaryKey(column);
		}
	}
	
}
