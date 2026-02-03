package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import net.sf.minuteProject.configuration.bean.enrichment.validation.ComparisonOperand;
import net.sf.minuteProject.configuration.bean.enrichment.validation.EntityTwoFieldComparison;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.StringUtils;

@Getter
@Setter
public class EntityValidationConvention extends EntityMultiFieldConvention {

	public void apply2FieldConvention(Table table, Column firstColumn, Column secondColumn) {
		EntityTwoFieldComparison etfc = new EntityTwoFieldComparison();
		Optional<ComparisonOperand> fromConventionValue = ComparisonOperand.fromConventionValue(type);
		if (fromConventionValue.isPresent()) {
			etfc.setOperand(fromConventionValue.get().getValue());
		}
		if (StringUtils.checkExpression(firstColumn.getName(), fieldPatternType, firstFieldPattern)) {
			etfc.setFirstFieldName(firstColumn.getName());
			etfc.setSecondFieldName(secondColumn.getName());
		} else {
			etfc.setFirstFieldName(secondColumn.getName());
			etfc.setSecondFieldName(firstColumn.getName());
		}
		table.getValidations().add(etfc);
	}

}
