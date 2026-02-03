package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Data;
import net.sf.minuteProject.configuration.bean.model.statement.Query;

@Data
public class ExistUniqueValidation<CoreElement> extends ValidationBean implements Validation<CoreElement>{
	
	private Query query;
	private String queryId;
	private String queryParams;

}
