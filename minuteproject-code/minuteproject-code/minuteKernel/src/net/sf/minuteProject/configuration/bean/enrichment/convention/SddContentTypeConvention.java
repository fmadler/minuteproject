package net.sf.minuteProject.configuration.bean.enrichment.convention;

import static net.sf.minuteProject.utils.TableUtils.liveBusinessDataContentType;
import static net.sf.minuteProject.utils.TableUtils.masterDataContentType;
import static net.sf.minuteProject.utils.TableUtils.pseudoStaticDataContentType;
import static net.sf.minuteProject.utils.TableUtils.referenceDataContentType;

import net.sf.minuteProject.configuration.bean.model.statement.Query;

public class SddContentTypeConvention extends SddConvention {

	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_STARTING_WITH = "apply-content-type-to-entity-starting-with";
	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_ENDING_WITH   = "apply-content-type-to-entity-ending-with";
	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_BELONGING_TO_PACKAGE  = "apply-content-type-to-entity-belonging-to-package";
	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE  = "apply-content-type-to-entity-not-belonging-to-package";
	
	protected String pattern, contentType;
//	
//	@Override
//	public void apply(StatementModel model) {
//		if (isValid()) {
//			model.getSddPackage().getQueryPackages()
//			.stream()
//			.flatMap(t -> t.getListOfQueries().stream())
//			.collect(Collectors.toList())
//			.stream()
//			.forEach (t -> apply(t));
//		}
//	}

	protected void apply(Query<?> query) {
		if (isMatch (query)) {
			query.setContentType(getContentType());
			//TODO add in cache-convention
			//query.setCache(true);
		}
	}

	protected boolean isMatch(Query<?> query) {
		if (APPLY_CONTENT_TYPE_TO_ENTITY_STARTING_WITH.equals(type))
			return isBeginningMatch(query, pattern);
		else if (APPLY_CONTENT_TYPE_TO_ENTITY_ENDING_WITH.equals(type))
			return isEndingMatch(query, pattern);
		else if (APPLY_CONTENT_TYPE_TO_ENTITY_BELONGING_TO_PACKAGE.equals(type))
			return isBelongsToPackage(query, pattern);
		else if (APPLY_CONTENT_TYPE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE.equals(type))
			return doesNotBelongToPackage(query, pattern);
		return false;
	}



	protected boolean isValid () {
		if ((APPLY_CONTENT_TYPE_TO_ENTITY_ENDING_WITH.equals(type) 
			|| APPLY_CONTENT_TYPE_TO_ENTITY_STARTING_WITH.equals(type) 
			|| APPLY_CONTENT_TYPE_TO_ENTITY_BELONGING_TO_PACKAGE.equals(type)
			|| APPLY_CONTENT_TYPE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE.equals(type)
			) 
			&& getPattern()!=null 
			&& (   pseudoStaticDataContentType.equals(getContentType())
			    || masterDataContentType.equals(getContentType())
			    || liveBusinessDataContentType.equals(getContentType())
			    || referenceDataContentType.equals(getContentType())
			    )
			)
			return true;
		return false;
	}
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
