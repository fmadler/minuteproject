package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils
import net.sf.minuteProject.utils.StringUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

public abstract class FieldConvention extends ModelConvention{

	String fieldPattern, fieldPatternType, fieldType
	List<String> fieldPatternList
	
	protected boolean match(Column column) {
		/*if (!column.isRequired()) //for the moment only apply on not nullable column
			return false;
			*/
		boolean matchFieldType = false
		boolean matchFieldPattern = false
		if (hasFieldType()) {
			if (ColumnUtils.isBoolean(column)) {
				matchFieldType = "boolean".equalsIgnoreCase(fieldType) || "bit".equalsIgnoreCase(fieldType)
			} else {
				matchFieldType = column.getType().equalsIgnoreCase(fieldType)
			}
		} else
			matchFieldType=true
		if (hasFieldPatternType() && hasFieldPattern()) {
			for (String fp : getFieldPatternList()) {
				matchFieldPattern = StringUtils.checkExpression(column.getName(), fieldPatternType, fp)
				if (matchFieldPattern)
					break;
			}
		} else
			matchFieldPattern = true
		//println "${column} and ${stereotype} and ${matchFieldType} && ${matchFieldPattern}"
		return matchFieldType && matchFieldPattern
	}
	
	public List<String> getFieldPatternList() {
		if (fieldPatternList==null) {
			fieldPatternList = ParserUtils.getList(fieldPattern)
		}
		fieldPatternList
	}
	
	protected boolean isValid() {
		hasFieldType() || (hasFieldPatternType() && hasFieldPattern()) ;
	}
	
	protected boolean hasFieldType() {
		fieldType!=null
	}
	
	protected boolean hasFieldPatternType() {
		fieldPatternType!=null
	}
	
	protected boolean hasFieldPattern() {
		fieldPattern!=null
	}
	

}
