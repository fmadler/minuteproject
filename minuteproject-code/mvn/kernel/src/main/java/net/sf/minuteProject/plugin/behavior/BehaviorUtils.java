package net.sf.minuteProject.plugin.behavior;

import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.ColumnUtils;

public class BehaviorUtils {

	private static final String PASSIVATE_BASED_ON_TOGGLE_FIELD = "passivate-based-on-toggle-field";

//	public boolean usePassivation (Table table, Template template) {
//		String columnName = getPassivationColumnName (template);
//		return ColumnUtils.getColumn(table, columnName)==null?false:true;
//	}
//	public boolean usePassivationProperty (Table table, Template template) {
//		return template.hasProperty(PASSIVATE_BASED_ON_TOGGLE_FIELD);
//	}
//	
//	public Column getPassivationColumn (Table table, Template template) {
//		Property property = template.getPropertyByName(PASSIVATE_BASED_ON_TOGGLE_FIELD); 
//		if (property==null)
//			return null;
//		Property propertyField = getPassivationColumnProperty (property);
//		if (propertyField==null)
//			return null;
//		return ColumnUtils.getColumn(table, propertyField.get)==null?false:true;
//	}

	private Property getPassivationColumnProperty(Property property) {
		if (property.getProperties().isEmpty())
			return null;
		return property.getProperties().get(0);
	}
	
	public PassivationBehavior getPassivationBehavior (Table table, Template template) {
		PassivationBehavior pb = new PassivationBehavior();
		Property property = template.getPropertyByName(PASSIVATE_BASED_ON_TOGGLE_FIELD); 
		if (property==null)
			return pb;
		Property propertyField = getPassivationColumnProperty (property);
		if (propertyField==null)
			return pb;
		pb.column = ColumnUtils.getColumn(table, propertyField.getName());
		pb.value = propertyField.getValue();
		if (pb.column != null)
			pb.hasPassivation=true;
		return pb;
	}
	
	public class PassivationBehavior {
		Column column;
		String value;
		boolean hasPassivation=false;
		
		public Column getColumn() {
			return column;
		}

		public String getValue() {
			return value;
		}

		public boolean hasPassivation() {
			return hasPassivation;
		}

		public String toString() {
			String name = (column!=null)?column.getName():"NO COLUMN FOUND";
			return "column = "+name+" - value "+value+" - hasPassivation "+hasPassivation;
		}
	}
}
