package net.sf.minuteProject.configuration.bean.enrichment.convention;

import lombok.Data;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryPagination;

@Data
public class SddPaginationConvention extends SddConvention {

	private String pattern, offsetParam, maxResultParam;
	private boolean addParamDefault;
	
	public static final String APPLY_PAGINATION_ONLY_TO_QUERY_ENDING_WITH = "apply-pagination-only-to-query-ending-with";

	@Override
	protected boolean isValid() {
		if (APPLY_PAGINATION_ONLY_TO_QUERY_ENDING_WITH.equals(type)
				&& pattern!=null 
			)
			return true;
		return false;
	}

	@Override
	protected void apply(Query<?> query) {
		if (isAppliable(query) && !query.hasPagination()) {
			QueryPagination qp = new QueryPagination();
			if (maxResultParam!=null) {
				qp.setMaxResultParam(maxResultParam);
			}
			if (offsetParam!=null) {
				qp.setOffsetParam(offsetParam);
			}
			query.setQueryPagination(qp);
		}
	}

	private final boolean isAppliable(Query<?> query) {
		if (APPLY_PAGINATION_ONLY_TO_QUERY_ENDING_WITH.equals(type) && isEndingMatch(query, pattern))
			return true;
		return false;
	}


}
