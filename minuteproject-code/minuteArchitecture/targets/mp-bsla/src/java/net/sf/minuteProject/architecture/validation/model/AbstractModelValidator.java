package net.sf.minuteProject.architecture.validation.model;

import net.sf.minuteProject.architecture.holder.element.ValidationModelError;

public class AbstractModelValidator {
	
    protected ValidationModelError validateMaxLength (String fieldName, String fieldValue, int acceptedLength) {
       if (fieldValue!=null) {
	      int fieldLength = fieldValue.length();
	      return validateMaxLength (fieldName, fieldLength, acceptedLength);
       } else
          return null;
    }
    
    protected ValidationModelError validateMaxLength (String fieldName, int fieldLength, int acceptedLength) {
    	if (fieldLength > acceptedLength )
           return new ValidationModelError(ValidationModelError.LENGTH_TOO_HIGH ,fieldName, Integer.valueOf(fieldLength), Integer.valueOf(acceptedLength));
        return null;
    }

    protected ValidationModelError validateMaxLength (String fieldName, long fieldLength, long acceptedLength) {
    	if (fieldLength > acceptedLength )
           return new ValidationModelError(ValidationModelError.LENGTH_TOO_HIGH ,fieldName, Long.valueOf(fieldLength), Long.valueOf(acceptedLength));
        return null;
    }
    
    protected ValidationModelError validatePresence (String fieldName, Object fieldValue, boolean isTobePresent) {
        if (isTobePresent == true && fieldValue==null)
           return new ValidationModelError(ValidationModelError.ABSENT ,fieldName);
        return null;
    }
    
    protected ValidationModelError validatePresence (String fieldName, String fieldValue, boolean isTobePresent) {
        if (isTobePresent == true && (fieldValue==null || fieldValue.equals("")))
           return new ValidationModelError(ValidationModelError.ABSENT ,fieldName);
        return null;
    }
    
}
