package net.sf.minuteProject.configuration.bean.model.data;

import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.enrichment.Trigger;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Derivation;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidation;
import net.sf.minuteProject.configuration.bean.enumeration.Scope;

/**
 * Represents a column in the database model.
 * 
 * @author Florian Adler
 */
public interface Column extends BaseColumn
{
    /**
     * Determines whether this column is a primary key column.
     * 
     * @return <code>true</code> if this column is a primary key column
     */
    public boolean isPrimaryKey();

    /**
     * Specifies whether this column is a primary key column.
     * 
     * @param primaryKey <code>true</code> if this column is a primary key column
     */
    public void setPrimaryKey(boolean primaryKey);

    /**
     * Determines whether this column is an auto-increment column.
     * 
     * @return <code>true</code> if this column is an auto-increment column
     */
    public boolean isAutoIncrement();

    /**
     * Specifies whether this column is an auto-increment column.
     * 
     * @param autoIncrement <code>true</code> if this column is an auto-increment column
     */
    public void setAutoIncrement(boolean autoIncrement);

    
    public Table getTable ();
    
    public boolean isVersion();
    
    public void setVersion(boolean isVersion);
	 
	public List<Trigger> getTriggers();
	 
	public void addTriggers(Trigger trigger);

	public void setDerivations(List<Derivation> derivations);
	
	public List<Derivation> getDerivations();
	
	public Scope getScope();
	
	public void setScope(Scope scope);
	
	public void setValidations(List<FieldValidation> fieldValidations);
	
	public List<FieldValidation> getValidations();
	
	public List<Field> getStructuredArray();
	
	/* remove start */
	public void setIsStructuredArray(boolean isArray);
	
	public boolean isStructuredArray();

	public void setSeparatorCharacters(String chars);
	
	public void setArrayColumns(String columns);
	
	public void setArrayColumnsType(String columnsType);

	public String getSeparatorCharacters();
	
	public String getArrayColumns();
	
	public String getArrayColumnsType();
	/* remove end */
}

