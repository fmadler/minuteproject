package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Derivation;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidation;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidationAmongValue;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidationMandatory;
import net.sf.minuteProject.configuration.bean.enumeration.Order;
import net.sf.minuteproject.model.db.type.FieldType;

@Getter
@Setter
public class Field extends AbstractConfiguration implements CoreElement {

	private Entity entity;
	private String linkToTargetEntity, linkToTargetField, linkReferenceAlias;
	private String bidirectional;
	private Stereotype stereotype;
	private String type, length, typeAlias;
	private boolean isMandatory=false, 
			isId=false,
			isKey=false,
			isHidden=false,
			isArray=false,
			isStructuredArray=false
			;
	private List<Trigger> triggers;
	private List<Derivation> derivations;
	private String ordering;
	private String value, defaultValue;
	private Boolean isSearchable,isEditable;
	private String separatorCharacters, arrayColumns, arrayColumnsType;
	private List<FieldValidation> fieldValidations = new ArrayList<>();

	public String getSizeOrDefault() {
		if (length!=null)
			return length;
		if (FieldType.VARCHAR.name().equals(type))
			return "255";
		else
			return "0";
	}
	
	public boolean isEnum () {
		if (hasProperty("checkconstraint")) {
			return true;
		}
		return false;
	}
	public boolean isBidirectional () {
		if (bidirectional!=null && bidirectional.equals("true"))
			return true;
		return false;
	}
	
	public String getLinkToTargetEntity() {
		return linkToTargetEntity;
	}

	public void setLinkToTargetEntity(String linkToTargetEntity) {
		this.linkToTargetEntity = linkToTargetEntity.toUpperCase();
	}

	public String getLinkToTargetField() {
		return linkToTargetField;
	}

	public void setLinkToTargetField(String linkToTargetField) {
		this.linkToTargetField = linkToTargetField.toUpperCase();
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getBidirectional() {
		return bidirectional;
	}

	
	public void setBidirectional(String bidirectional) {
		this.bidirectional = bidirectional;
	}

	public Stereotype getStereotype() {
		return stereotype;
	}

	public void setStereotype(Stereotype stereotype) {
		this.stereotype = stereotype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {

		this.isMandatory = isMandatory;
		addFieldValidation(new FieldValidationMandatory());
	}

	public void setIsMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
		addFieldValidation(new FieldValidationMandatory());
	}

	public boolean isId() {
		return isId;
	}

	public void setId(boolean isId) {
		this.isId = isId;
	}
	
	public boolean isKey() {
		return isKey;
	}
	
	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	public void setIsId(boolean isId) {
		this.isId = isId;
	}

	public Boolean isSearchable() {
		return isSearchable;
	}

	public void setSearchable(boolean isSearchable) {
		this.isSearchable = isSearchable;
	}

	public Boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public List<Trigger> getTriggers() {
		if (triggers==null) triggers = new ArrayList<Trigger>();
		return triggers;
	}

	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}
	
	public void addTrigger (Trigger trigger) {
		getTriggers().add(trigger);
	}
	
	public String getLinkReferenceAlias() {
		return linkReferenceAlias;
	}

	public void setSeparatorCharacters(String separatorCharacters) {
		this.separatorCharacters = separatorCharacters;
	}
	
	public void setLinkReferenceAlias(String linkReferenceAlias) {
		this.linkReferenceAlias = linkReferenceAlias;
	}

	public List<Derivation> getDerivations() {
		if (derivations==null)
			derivations = new ArrayList<Derivation>();
		return derivations;
	}

	public void addDerivation(Derivation derivation) {
		getDerivations().add(derivation);
	}

	public void setTypeAlias(String typeAlias) {
		this.typeAlias = typeAlias;
	}

	public String getTypeAlias() {
		return typeAlias;
	}

	public String getOrdering() {
		return ordering;
	}

	public void setOrdering(String ordering) {
		this.ordering = ordering;
	}
	public Order getOrder() {
		return Order.getOrder(ordering);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<FieldValidation> getFieldValidations() {
		return fieldValidations;
	}
	public void setFieldValidations(List<FieldValidation> fieldValidations) {
		this.fieldValidations = fieldValidations;
	}
	public void addFieldValidation(FieldValidation fieldValidation) {
		getFieldValidations().add(fieldValidation);
	}
	
	public String toString() {
		return "field [name="+name+"]";
	}
}
