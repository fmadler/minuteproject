package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.validation.ComparisonOperand;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.StringUtils;

@Log4j
@Getter
@Setter
public abstract class EntityMultiFieldConvention extends ModelConvention {

	//List<FieldValidation> fieldValidations = new ArrayList<>();
	protected String firstFieldPattern, secondFieldPattern, thirdFieldPattern, fieldPatternType, type;
	
	@Override
	public void apply(BusinessModel model) {
		if (isValid()) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		} else
			log.error("EntityMultiFieldConvention not valid");
	}

	public void apply(Table table) {//TODO test
		// strip ending or beginning of column.name
		// check duplicate remaining + type
		// add convention
		
		List<Column> collect = Arrays.asList(table.getColumns()).stream()
			.filter(u -> {
				boolean firstOk = StringUtils.checkExpression(u.getName(), fieldPatternType, firstFieldPattern);
				boolean secondOk = StringUtils.checkExpression(u.getName(), fieldPatternType, secondFieldPattern);
				return (firstOk && !secondOk) || (secondOk && !firstOk);
			})
			.collect(Collectors.toList());
		//TODO can have multiple sets group by beginning or ending
		if (collect.size()==2) {
			Column firstColumn = collect.get(0);
			Column secondColumn = collect.get(1);
			apply2FieldConvention(table, firstColumn, secondColumn);
		}

	}


	public abstract void apply2FieldConvention(Table table, Column firstColumn, Column secondColumn);
	
	protected boolean isValid() {
		return firstFieldPattern!=null 
				&& secondFieldPattern!=null 
				&& fieldPatternType!=null 
				&& type!=null
				&& ComparisonOperand.fromConventionValue(type).isPresent()
				;
	}

}
