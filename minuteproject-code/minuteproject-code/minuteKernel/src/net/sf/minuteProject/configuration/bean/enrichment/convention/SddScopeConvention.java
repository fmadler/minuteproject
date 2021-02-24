package net.sf.minuteProject.configuration.bean.enrichment.convention;

import lombok.Data;
import net.sf.minuteProject.configuration.bean.model.statement.Query;

@Data
public class SddScopeConvention extends SddConvention {

	public static final String APPLY_SCOPE_TO_ENTITY_STARTING_WITH 				= "apply-scope-to-entity-starting-with";
	public static final String APPLY_SCOPE_TO_ENTITY_ENDING_WITH   				= "apply-scope-to-entity-ending-with";
	public static final String APPLY_SCOPE_TO_ENTITY_CONTAINING   				= "apply-scope-to-entity-containing";
	
	private String pattern, scope;
	
	protected void apply(Query<?> query) {
		if (isMatch (query)) {
			query.setScope(scope);
		}
	}

	protected boolean isMatch(Query<?> query) {
		if (APPLY_SCOPE_TO_ENTITY_STARTING_WITH.equals(type))
			return isBeginningMatch(query, pattern);
		else if (APPLY_SCOPE_TO_ENTITY_ENDING_WITH.equals(type))
			return isEndingMatch(query, pattern);
		else if (APPLY_SCOPE_TO_ENTITY_CONTAINING.equals(type))
			return containsMatch(query, pattern);
		return false;
	}
	
	protected boolean isValid () {
		if ((APPLY_SCOPE_TO_ENTITY_STARTING_WITH.equals(type) 
			|| APPLY_SCOPE_TO_ENTITY_ENDING_WITH.equals(type) 
			|| APPLY_SCOPE_TO_ENTITY_CONTAINING.equals(type)
			) 
			&& getPattern()!=null
			&& getScope()!=null
			)
			return true;
		return false;
	}

}
