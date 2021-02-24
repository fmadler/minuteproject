package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.sf.minuteProject.configuration.bean.enrichment.group.FieldGroup;
import net.sf.minuteProject.configuration.bean.enrichment.validation.ComparisonOperand;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.CollectionUtils;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.StringUtils;

@Log4j
@Getter
@Setter
public class EntityDisplayConvention extends EntityMultiFieldConvention {

	public void apply2FieldConvention(Table table, Column firstColumn, Column secondColumn) {
		applyFieldsConvention (table, firstColumn, secondColumn);
	}
	public void applyFieldsConvention(Table table, Column... columns) {

		FieldGroup fg = new FieldGroup();
/*		
		if (StringUtils.checkExpression(firstColumn.getName(), fieldPatternType, firstFieldPattern)) {
			fg.setFields(firstColumn.getName()+","+secondColumn.getName());
		} else {
			fg.setFields(secondColumn.getName()+","+firstColumn.getName());
		}
		*/
		String collect = Arrays.asList(columns).stream()
			.filter(Objects::nonNull)
			.map(Column::getName)
			.collect(Collectors.joining(","));
		
		fg.setFields(collect);
		
		if(fieldNoAlreadyInFieldGroup(table, fg)) {
			table.getFieldGroups().add(fg);
		}
	}

	private boolean fieldNoAlreadyInFieldGroup(Table table, FieldGroup fg) {
		return !table.getFieldGroups().stream()
			.flatMap(u -> u.getFieldList().stream())
			.filter(u -> fg.getFieldList().contains(u))
			.findFirst()
			.isPresent();
	}
	protected boolean isValid() {
		return firstFieldPattern!=null 
				&& secondFieldPattern!=null 
				&& fieldPatternType!=null 
				&& ComparisonOperand.FIELD_GROUP.getConventionValue().equals(type)
				;
	}
	

	public void apply(Table table) {
		//TODO do not apply is column already part of display
		//TODO use column instead of String
		//TODO multiple field pattern
		List<String> collect = Arrays.asList(table.getColumns()).stream()
				.map(Column::getName)
				.collect(Collectors.toList())
				;
		Map<String, List<String>> group = CollectionUtils.getGroup(collect, fieldPatternType, firstFieldPattern, secondFieldPattern, thirdFieldPattern);
		group.entrySet().stream()
			.filter(e -> e.getValue().size()>=2)
			.forEach(a -> {
				applyFieldsConvention(table, 
						ColumnUtils.getColumn(table, a.getValue().get(0)), 
						ColumnUtils.getColumn(table, a.getValue().get(1)),
						a.getValue().size()==3?ColumnUtils.getColumn(table, a.getValue().get(2)):null
						);
			});
		

	}
	

}
