package net.sf.minuteProject.configuration.bean.enrichment.convention;

import static net.sf.minuteProject.utils.TableUtils.liveBusinessDataContentType;
import static net.sf.minuteProject.utils.TableUtils.masterDataContentType;
import static net.sf.minuteProject.utils.TableUtils.pseudoStaticDataContentType;
import static net.sf.minuteProject.utils.TableUtils.referenceDataContentType;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class EntityContentTypeConvention extends ModelConvention {

	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_STARTING_WITH = "apply-content-type-to-entity-starting-with";
	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_ENDING_WITH   = "apply-content-type-to-entity-ending-with";
	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_CONTAINING   = "apply-content-type-to-entity-containing";
	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_BELONGING_TO_PACKAGE  = "apply-content-type-to-entity-belonging-to-package";
	public static final String APPLY_CONTENT_TYPE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE  = "apply-content-type-to-entity-not-belonging-to-package";
	
	private String pattern, contentType;
	
	@Override
	public void apply(BusinessModel model) {
		if (isValid()) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		}
	}

	private void apply(Table table) {
		if (isMatch (table))
			table.setContentType(getContentType());
	}

	private boolean isMatch(Table table) {
		if (APPLY_CONTENT_TYPE_TO_ENTITY_STARTING_WITH.equals(type))
			return isBeginningMatch(table);
		else if (APPLY_CONTENT_TYPE_TO_ENTITY_ENDING_WITH.equals(type))
			return isEndingMatch(table);
		else if (APPLY_CONTENT_TYPE_TO_ENTITY_CONTAINING.equals(type))
			return containsMatch(table);
		else if (APPLY_CONTENT_TYPE_TO_ENTITY_BELONGING_TO_PACKAGE.equals(type))
			return isBelongsToPackage(table);
		else if (APPLY_CONTENT_TYPE_TO_ENTITY_NOT_BELONGING_TO_PACKAGE.equals(type))
			return doesNotBelongToPackage(table);
		return false;
	}

	private boolean isBeginningMatch(Table table) {
		String tableNameLc = table.getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (tableNameLc.startsWith(s))
				return true;
		}
		return false;
	}
	
	private boolean isEndingMatch(Table table) {
		String tableNameLc = table.getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (tableNameLc.endsWith(s))
				return true;
		}
		return false;
	}
	
	private boolean containsMatch(Table table) {
		String tableNameLc = table.getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (tableNameLc.contains(s))
				return true;
		}
		return false;
	}
	
	private boolean isBelongsToPackage(Table table) {
		String packageNameLowerCase = table.getPackage().getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (packageNameLowerCase.endsWith(s))
				return true;
		}
		return false;
	}
	
	private boolean doesNotBelongToPackage(Table table) {
		return isBelongsToPackage(table);
	}

	private boolean isValid () {
		if ((APPLY_CONTENT_TYPE_TO_ENTITY_ENDING_WITH.equals(type) 
			|| APPLY_CONTENT_TYPE_TO_ENTITY_STARTING_WITH.equals(type) 
			|| APPLY_CONTENT_TYPE_TO_ENTITY_BELONGING_TO_PACKAGE.equals(type)
			|| APPLY_CONTENT_TYPE_TO_ENTITY_CONTAINING.equals(type)) 
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
