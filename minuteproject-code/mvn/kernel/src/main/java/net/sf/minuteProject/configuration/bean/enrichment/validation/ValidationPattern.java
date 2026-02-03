package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Data;
import lombok.Getter;

@Data
public class ValidationPattern extends ValidationBean implements FieldValidation{

	private String type;
	private String regex;
	

    protected static final String LATITUDE_PATTERN="^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
    protected static final String LONGITUDE_PATTERN="^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";
    
	@Getter
	public enum ValidationType {
		EMAIL,// ("@javax.validation.constraints.Email"),
		LATITUDE (LATITUDE_PATTERN),
		LONGITUDE (LONGITUDE_PATTERN); //ADD regex @Longitude, @Latitude ​https://gist.github.com/ferrarimarco/e73e07d6664281a81d95
		
		private final String regex;
		
		ValidationType () {
			this.regex = null;
		}
		
		ValidationType (String regex) {
			this.regex = regex;
		}
	}
	
	public ValidationType getValidationType() {
		if (type==null) return null;
		for(ValidationType vt : ValidationType.values()) {
			if (type.equalsIgnoreCase(vt.name())) {
				return vt;
			}
		}
		return null;
	}
	
}
