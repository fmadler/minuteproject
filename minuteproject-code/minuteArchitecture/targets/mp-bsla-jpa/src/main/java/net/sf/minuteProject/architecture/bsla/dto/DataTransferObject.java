package net.sf.minuteProject.architecture.bsla.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DataTransferObject implements Serializable{

	public String toString() {
	   return ToStringBuilder.reflectionToString(this);
	} 
	
	public boolean toEquals(Object thiz, Object object) {
	 	return EqualsBuilder.reflectionEquals(thiz, object);
	}
	

}
