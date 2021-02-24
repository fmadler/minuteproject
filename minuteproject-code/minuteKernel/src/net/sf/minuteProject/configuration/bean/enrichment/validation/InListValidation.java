package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Data;
import net.sf.minuteProject.configuration.bean.enrichment.Field;

@Data
public class InListValidation extends ValidationBean implements Validation<Field>{
	
	private String list;

}
