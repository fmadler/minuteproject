package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.FormatUtils;

public class AbstractConfiguration extends BeanCommon implements GeneratorBean {
	// TODO IDEALLY set the abstractConfiguration properties after every creation in the digester
	// Done by AOP
	
	protected boolean isCacheEnabled;
	protected String alias, label, comment;
	private enum PropertyCriteria {NAME, TAG};
	protected String name;
	private String refname;
	private String description;
	private List<Property> properties;
	private GeneratorBean parent;
	private String configurationFileInClassPath;
	private boolean isAliasFormatted;
	private boolean isAliasSet;
	private String displayName;
	private int displayOrder;
	
	public GeneratorBean getParent() {
		return parent;
	}
	public void setParent(GeneratorBean parent) {
		this.parent = parent;
	}
	
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	public List<Property> getProperties() {
		if (properties == null) 
			properties = new ArrayList<Property>();
		return properties;
	}
	
	public void addProperty (Property property) {
		getProperties().add(property);
	}
	
	public Property[] getPropertiesArray() {
		properties = getProperties();
		return (Property[]) properties.toArray(new Property[properties.size()]);
	}
	
	public boolean hasProperty (String name) {
		if (getPropertyValue(name)!=null)
			return true;
		return false;
	}

	public Boolean hasPropertyValue(String name, String value) {
		String s = getPropertyValue(name);
		return (s!=null)?(s.equals(value)):false;
	}
	
	public Boolean hasPropertyStartWithValue(String name, String value) {
		return getPropertyStartWithValue(name)!=null;
	}
	
	public boolean hasPropertyByName (String name) {
		if (getPropertyByName(name)!=null)
			return true;
		return false;
	}
	
	public Property getPropertyByName (String name) {
		return getPropertyByCriteria(PropertyCriteria.NAME, name);
	}

	public Property getPropertyByTag (String tag) {
		return getPropertyByCriteria(PropertyCriteria.TAG, tag);
	}
	
	public List<Property> getPropertyListByTag (String value) {
	    return getPropertyListByCriteria(PropertyCriteria.TAG, value); 
	}

	public List<Property> getPropertyListByCriteria (PropertyCriteria propertyCriteria, String value) {
	    //TODO refactor
		List<Property> properties = new ArrayList<Property>();	
		for (Property property : getProperties()) {
			if (propertyCriteria.equals(PropertyCriteria.NAME))
				if (value.equals(property.getName()))
					properties.add(property);
			if (propertyCriteria.equals(PropertyCriteria.TAG))
				if (value.equals(property.getTag()))
					properties.add(property);			
		}
		return properties;
	}
	
	public Property getPropertyByCriteria (PropertyCriteria propertyCriteria, String value) {
		for (Property property : getProperties()) {
			if (propertyCriteria.equals(PropertyCriteria.NAME))
				if (value.equals(property.getName()))
					return property;
			if (propertyCriteria.equals(PropertyCriteria.TAG))
				if (value.equals(property.getTag()))
					return property;			
		}
		return null;
	}
//	
	public String getPropertyValue (String name) {
		if (name==null) return null;
		for (Property property : getProperties()) {
			if (property.getName()==null)
				return null;
			if (property.getName().equals(name))
				return property.getValue();
		}
		return null;
	}
	public String getPropertyStartWithValue (String name) {
		if (name==null) return null;
		for (Property property : getProperties()) {
			if (property.getName()==null)
				return null;
			if (property.getName().startsWith(name))
				return property.getValue();
		}
		return null;
	}
	
	public boolean getIsTrueProperty (String name) {
		String value = getPropertyValue(name);
		if (value==null)
			return false;
		return value.equals("true");
	}	
	
	public boolean getIsFalseProperty (String name) {
		String value = getPropertyValue(name);
		if (value==null)
			return true;
		return value.equals("false");
	}	

	public void addProperties(List<Property> props) {
		getProperties().addAll(props);
		
	}

	public String getTechnicalPackage(Template template)
	{
		return template.getTechnicalPackage()!=null?template.getTechnicalPackage():"";
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRefname() {
		return refname;
	}
	public void setRefname(String refname) {
		this.refname = refname;
	}
	public Package getPackage() {
		return null;
	}
	public void setPackage(Package pack) {
	}

	public String getGeneratedBeanName() {
		return FormatUtils.getJavaName(getAlias());
	}
	
	public String getConfigurationFileInClassPath() {
		return configurationFileInClassPath;
	}
	public void setConfigurationFileInClassPath(String configurationFileInClassPath) {
		this.configurationFileInClassPath = configurationFileInClassPath;
	}
	
	public String getAlias() {
		if (alias==null || alias.equals(""))
			alias = getName();
		else if (!isAliasFormatted) {
			isAliasFormatted = true;
			alias = formatAlias(alias);
		}
		return alias;
	}
	
	private String formatAlias(String alias) {
		alias= StringUtils.upperCase(alias.replace(" ", "_"));
		alias= StringUtils.upperCase(alias.replace("-", "_"));
		return StringUtils.upperCase(alias.replace(" ", "_"));
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void enableCache() {
		isCacheEnabled = true;
	}
	
	public String getLabel() {
		if (label==null)
			label=getAlias();
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean isAliasSet() {
		return isAliasSet;
	}
	
	public void setIsAliasSet (boolean isAliasSet) {
		this.isAliasSet = isAliasSet;
	}
	public String getDisplayName() {
		return displayName!=null?displayName:getLabel();
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public boolean hasInjectedDisplayName() {
		return !getDisplayName().equals(getName());
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	
}
