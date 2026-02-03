package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidation;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;

@Log4j
@Getter
@Setter
public class FieldValidationConvention extends FieldConvention {

	List<FieldValidation> fieldValidations = new ArrayList<>();
	@Override
	public void apply(BusinessModel model) {
		if (isValid()) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		} else
			log.error("FieldValidationConvention not valid");
	}

	public void apply(Table table) {
		for (Column column : table.getColumns()) {
			if (match(column)) {
				column.setValidations(fieldValidations);
			}
		}
	}
	
	protected boolean match(Column column) {
		if (!column.isRequired()) //for the moment only apply on not nullable column
			return false;
		else 
			return super.match(column);
	}
	
	protected boolean isValid() {
		return super.isValid() && getFieldValidations().size()>0;
	}
	public void addFieldValidation(FieldValidation fieldValidation) {
		getFieldValidations().add(fieldValidation);
	}
}
