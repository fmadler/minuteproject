package net.sf.minuteProject.configuration.bean.enrichment.convention;

import lombok.Data;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.StringUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

import java.util.List;

@Data
public abstract class FieldConvention extends ModelConvention{

	private String fieldPattern, fieldPatternType, fieldType;
	private Integer fieldMinLength, fieldMaxLength;
	private List<String> fieldPatternList;
	
	protected boolean match(Column column) {
		/*if (!column.isRequired()) //for the moment only apply on not nullable column
			return false;
			*/
		boolean matchFieldType = false;
		boolean matchFieldPattern = false;
		if (hasFieldType()) {
			if (ColumnUtils.isBoolean(column)) {
				matchFieldType = "boolean".equalsIgnoreCase(fieldType) || "bit".equalsIgnoreCase(fieldType);
			} else {
				matchFieldType = column.getType().equalsIgnoreCase(fieldType);
			}
		} else
			matchFieldType=true;
		if (hasFieldPatternType() && hasFieldPattern()) {
			for (String fp : getFieldPatternList()) {
				matchFieldPattern = StringUtils.checkExpression(column.getName(), fieldPatternType, fp);
				if (matchFieldPattern)
					break;
			}
		} else
			matchFieldPattern = true;
		//println "${column} and ${stereotype} and ${matchFieldType} && ${matchFieldPattern}"
		return matchFieldType && matchFieldPattern;
	}
	
	public List<String> getFieldPatternList() {
		if (fieldPatternList==null) {
			fieldPatternList = ParserUtils.getList(fieldPattern);
		}
		return fieldPatternList;
	}
	
	protected boolean isValid() {
		return hasFieldType() || (hasFieldPatternType() && hasFieldPattern()) ;
	}
	
	protected boolean hasFieldType() {
		return fieldType!=null;
	}
	
	protected boolean hasFieldPatternType() {
		return fieldPatternType!=null;
	}
	
	protected boolean hasFieldPattern() {
		return fieldPattern!=null;
	}
	

}
