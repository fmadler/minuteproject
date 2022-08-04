package net.sf.minuteProject.utils.sql;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParamLink;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class QueryParamLinkUtils {

	public static String AUTOCOMPLETE = "autocomplete";
	public static boolean isAutocompletion(QueryParamLink queryParamLink) {
		return AUTOCOMPLETE.equals(queryParamLink.getDisplayMode());
	}
	public static List<Column> getSemanticReferenceColumns(QueryParamLink queryParamLink, Query query) {
		List<Column> columns = new ArrayList<Column>();
		for (String colName : ParserUtils.getList(queryParamLink.getSemanticReferenceFields())) {
			Column column = ColumnUtils.getColumn(query.getOutputBean(), colName);
			if (column!=null)
				columns.add(column);
		}
		return columns;
	}
	
	public static String getAutocompleteFilter(QueryParamLink queryParamLink) {
		if (isAutocompletion(queryParamLink)) {
			String filter = queryParamLink.getAutoCompleteFilter();
			return (filter!=null) ? filter : "startswith";
		}
		return "startswith";
	}
}
