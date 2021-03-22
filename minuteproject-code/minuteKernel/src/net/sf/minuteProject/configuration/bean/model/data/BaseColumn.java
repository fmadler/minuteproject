package net.sf.minuteProject.configuration.bean.model.data;

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.enrichment.Stereotype;
import net.sf.minuteProject.configuration.bean.enumeration.Order;
import net.sf.minuteProject.configuration.bean.model.statement.QueryParamLink;

public interface BaseColumn extends GeneratorBean{
	 
    /**
     * Returns the name of the column.
     * 
     * @return The name
     */
    public String getName();

    /**
     * Sets the name of the column.
     * 
     * @param name The name
     */
    public void setName(String name);

    /**
     * Returns the java name of the column. This property is unused by DdlUtils and only
     * for Torque compatibility.
     * 
     * @return The java name
     */
    public String getJavaName();


    /**
     * Sets the java name of the column. This property is unused by DdlUtils and only
     * for Torque compatibility.
     * 
     * @param javaName The java name
     */
    public void setJavaName(String javaName);

    /**
     * Returns the code (one of the constants in {@link java.sql.Types}) of the
     * JDBC type of the column.
     * 
     * @return The type code
     */
    public int getTypeCode();

    /**
     * Sets the code (one of the constants in {@link java.sql.Types}) of the
     * JDBC type of the column. 
     * 
     * @param typeCode The type code
     */
    public void setTypeCode(int typeCode);
    
    /**
     * Returns the JDBC type of the column.
     * 
     * @return The type
     */
    public String getType();

    /**
     * Sets the JDBC type of the column.
     *
     * @param type The type
     */
    public void setType(String type);

    /**
     * Determines whether this column is of a numeric type.
     * 
     * @return <code>true</code> if this column is of a numeric type
     */
    public boolean isOfNumericType();

    /**
     * Determines whether this column is of a text type.
     * 
     * @return <code>true</code> if this column is of a text type
     */
    public boolean isOfTextType();

    /**
     * Determines whether this column is of a binary type.
     * 
     * @return <code>true</code> if this column is of a binary type
     */
    public boolean isOfBinaryType();

    /**
     * Determines whether this column is of a special type.
     * 
     * @return <code>true</code> if this column is of a special type
     */
    public boolean isOfSpecialType();
    
    /**
     * Returns the size of the column.
     * 
     * @return The size
     */
    public String getSize();

    /**
     * Returns the size of the column as an integer.
     * 
     * @return The size as an integer
     */
    public int getSizeAsInt();

    /**
     * Sets the size of the column. This is either a simple integer value or
     * a comma-separated pair of integer values specifying the size and scale.
     * 
     * @param size The size
     */
    public void setSize(String size);
    
    /**
     * Returns the scale of the column.
     * 
     * @return The scale
     */
    public int getScale();

    /**
     * Sets the scale of the column.
     *
     * @param scale The scale
     */
    public void setScale(int scale);

    /**
     * Sets both the size and scale.
     * 
     * @param size  The size
     * @param scale The scale
     */
    public void setSizeAndScale(int size, int scale);
    
    /**
     * Returns the precision radix of the column.
     * 
     * @return The precision radix
     */
    public int getPrecisionRadix();

    /**
     * Sets the precision radix of the column.
     * 
     * @param precisionRadix The precision radix
     */
    public void setPrecisionRadix(int precisionRadix);

    /**
     * Returns the default value of the column.
     * 
     * @return The default value
     */
    public String getDefaultValue();
    
    /**
     * Sets the default value of the column. Note that this expression will be used
     * within quotation marks when generating the column, and thus is subject to
     * the conversion rules of the target database.
     * 
     * @param defaultValue The default value
     */
    public void setDefaultValue(String defaultValue);

    /**
     * Determines whether this column is a required column, ie. that it is not allowed
     * to contain <code>NULL</code> values.
     * 
     * @return <code>true</code> if this column is a required column
     */
    public boolean isRequired();
    
    /**
     * Specifies whether this column is a required column, ie. that it is not allowed
     * to contain <code>NULL</code> values.
     * 
     * @param required <code>true</code> if this column is a required column
     */
    public void setRequired(boolean required);
    //added methods
    public String toVerboseString();

    public boolean isLob();

    public void setStereotype(Stereotype stereotype);
    
    public Stereotype getStereotype();
    
    public int minLength();
    
    public void setMinLength(int minLength);

    public String getTypeAlias();
    
    public void setTypeAlias(String typeAlias);
    
    public boolean isHidden();
    
    public void setHidden(Boolean isHidden);
    
	public void setSearchable(Boolean isSearchable);
	
	public boolean isSearchable();
	 
	public void setEditable(Boolean isSearchable);
	
	public boolean isEditable();
	
	public void setTransient(Boolean isTransient);
	 
	public boolean isTransient();
	
	public void setContext(boolean isContext);
	
	public boolean isContext();
	
	public void setImplicit(boolean isImplicit);
	
	public boolean isImplicit();
	
	public void setFilterName(String filterName);
	
	public void setSessionParamName(String sessionParamName);

	public void setUseJwtSubject(boolean useJwtSubject);

	public String getSessionParamName();

	public boolean useJwtSubject();
	
	public String getFilterName ();
	
	public boolean isFilter();
	
	public void setHasBeenDuplicated(boolean hasBeenDuplicated);
	
	public boolean hasBeenDuplicated(); // duplication by choice
	
	public QueryParamLink getQueryParamLink ();
	
	public void setQueryParamLink (QueryParamLink queryParamLink);
	
	public void setIsOutputParam (boolean isOutputParam);
	
	public boolean isOutputParam();
	
	public boolean isArray ();
	
	public void setIsArray(boolean isArray);

}
