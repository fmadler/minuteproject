package net.sf.minuteProject.architecture.bsla.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class AbstractDomainObject implements DomainObject {
    
	/**
	 * Default toString
	 */
	public String toString(Object object) {
		ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE); //SHORT_PREFIX_STYLE
	 	return  ToStringBuilder.reflectionToString(object);
	} 
	
	public int toHashCode(Object object) {
	 	return HashCodeBuilder.reflectionHashCode(object);
	}
	
	public boolean toEquals(Object thiz, Object object) {
	 	return EqualsBuilder.reflectionEquals(thiz, object);
	}
	
    public abstract boolean equalsMask(Object object) ;
    
    public abstract AbstractDomainObject mask(String field) ;
    
    public abstract AbstractDomainObject clone() ;
	
	public final static Timestamp     timestampMask__    = new java.sql.Timestamp(new java.util.Date().getTime());
	public final static String        stringMask__       = new String();
	public final static Long          longMask__         = Long.valueOf(-1);
	public final static Integer       integerMask__      = Integer.valueOf(-1);
	public final static Short         shortMask__        = Short.valueOf("-1");
	public final static BigInteger    bigIntegerMask__   = BigInteger.valueOf(-1);
	public final static BigDecimal    bigDecimalMask__   = BigDecimal.valueOf(-1);
	public final static Date          dateMask__         = new Date(new java.util.Date().getTime());
	
}	
