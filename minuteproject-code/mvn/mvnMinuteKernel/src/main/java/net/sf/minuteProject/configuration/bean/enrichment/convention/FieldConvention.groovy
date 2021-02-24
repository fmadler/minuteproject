package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.List;
import net.sf.minuteProject.utils.StringUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;
import net.sf.minuteProject.configuration.bean.model.data.Column;

public abstract class FieldConvention extends ModelConvention{

	String fieldPattern, fieldPatternType, fieldType
	List<String> fieldPatternList
	
	protected boolean match(Column column) {
		boolean matchFieldType = false
		boolean matchFieldPattern = false
		if (hasFieldType()) {
			matchFieldType = column.getType().toLowerCase().equals(fieldType.toLowerCase())
		} else
			matchFieldType=true
		if (hasFieldPatternType() && hasFieldPattern()) {
			for (String fp : getFieldPatternList()) {
				//println "${column} and ${stereotype} and ${fp}"
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
	
	private boolean hasFieldType() {
		fieldType!=null
	}
	
	private boolean hasFieldPatternType() {
		fieldPatternType!=null
	}
	
	private boolean hasFieldPattern() {
		fieldPattern!=null
	}
	

}
