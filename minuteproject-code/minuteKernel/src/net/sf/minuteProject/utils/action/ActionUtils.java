package net.sf.minuteProject.utils.action;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.utils.FormatUtils;

class ActionUtils {

	public final String SHOW_DETAILS = "showDetails";
	
	boolean isReservedAction(Action action) {
		if (SHOW_DETAILS.equals(action.getDefaultImplementation()))
			return true;
		return false;
	}
	String getActionLabel (Action action) {
		if (!StringUtils.isEmpty(action.getName()))
			return action.getName();
		else
			return action.getDescription();
	}
	
	String getActionMethod (Action action) {
		if (SHOW_DETAILS.equals(action.getDefaultImplementation()))
			return SHOW_DETAILS;
		return FormatUtils.getEachWordFirstLetterUpper(action.getName(), " ");
	}
	
	String getTargetUrl (Action action, Model model) {
		String queryId = action.getQueryId();
		Query query = model.getStatementModel().getQueryByIdOrName(queryId);
		if (query!=null)
			return "/html/sdd/"+FormatUtils.getJavaName(query.getName())+"In";
		return "/data/sdd/"+queryId+"In";
	}
	
	Query getQuery(Action action, Model model) {
		String queryId = action.getQueryId();
		return model.getStatementModel().getQueryByIdOrName(queryId);
	}
	
}
