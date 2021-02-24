package net.sf.minuteProject.architecture.validation.rule;

import java.util.regex.*;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.architecture.holder.element.SimpleValidationError;
import net.sf.minuteProject.architecture.holder.element.ValidationModelError;

public class SimpleRule {

	public static final String FIELD_MANDATORY_NOT_NULL = "FIELD_MANDATORY_NOT_NULL";
	public static final String INVALID_EMAIL = "INVALID_EMAIL";
	
	public static final String EMAIL_REGEX = "\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b";
	
//	
//	public static boolean isValidEmailAddress(String aEmailAddress){
//	    if (aEmailAddress == null) return false;
//	    boolean result = true;
//	    try {
//	      InternetAddress emailAddr = new InternetAddress(aEmailAddress);
//	      if ( ! hasNameAndDomain(aEmailAddress) ) {
//	        result = false;
//	      }
//	    }
//	    catch (AddressException ex){
//	      result = false;
//	    }
//	    return result;
//    }
//
//	  private static boolean hasNameAndDomain(String aEmailAddress){
//	    String[] tokens = aEmailAddress.split("@");
//	    return 
//	     tokens.length == 2 ;
////	    &&
////	     Util.textHasContent( tokens[0] ) && 
////	     Util.textHasContent( tokens[1] ) ;
//	  }

    public static SimpleValidationError validateNotNull (Object o, String errorCode) {
    	if (o==null)
    		return new SimpleValidationError(errorCode);
    	return null;
    }
    
    public static SimpleValidationError validateMandatoryNotBlank (String field) {
    	return validateMandatoryNotBlank(field, FIELD_MANDATORY_NOT_NULL);
    }
    
    public static SimpleValidationError validateMandatoryNotBlank (String field, String errorCode) {
        if (field!=null && StringUtils.trimToNull(field)!=null) {
  	       return null;
        } else
        return new SimpleValidationError(errorCode);
     }
    
    public static SimpleValidationError validateEmail (String field) {
    	return validateEmail(field, INVALID_EMAIL);
    }
    
    public static SimpleValidationError validateEmail (String field, String errorCode) {
    	return validateAgainstRegex(field, EMAIL_REGEX, errorCode);
    }
   
    public static SimpleValidationError validateAgainstRegex (String field, String regex, String errorCode ) {
    	if (validateAgainstRegex(field, regex)==true) {
//    		System.out.println("field="+field+" is valid");
    		return null;
    	}
    	return new SimpleValidationError(errorCode);
    } 
    
    public static boolean validateAgainstRegex (String field, String regex) {
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(field);
    	//        return matcher.find();
    	return matcher.matches();
    } 
}
