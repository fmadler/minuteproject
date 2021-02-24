package net.sf.minuteProject.plugin.vaadin;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import static net.sf.minuteProject.plugin.presentation.PresentationUtils.*;
import net.sf.minuteProject.plugin.presentation.PresentationUtils;
import net.sf.minuteProject.utils.ConvertUtils;

public class VaadinPlugin {

	public List<VaadinApplication> getVaadinApplications (Model model) {
		List<VaadinApplication> list = new ArrayList<VaadinApplication>();
		Table[] tables = model.getDataModel().getDatabase().getEntities();
		for (Table table : tables) {
			if (isVaadinApplication(table)) 
				list.add(new VaadinApplication(table));
		}
		return list;
	}

	public static int getDisplayColumns(Column column) {
		if (isTextArea(column))
			return Math.min(column.getSizeAsInt(),MAX_COLUMNS_DISPLAY_SIZE_TEXTAREA);
		return PresentationUtils.getDisplayColumns(column);
	}

	public String getColumnLengthInPixel (Column column) {
		return getDisplayColumns(column)*5+"px";
	}
	public static int getDisplayRows(Column column) {
		if (isTextArea(column))
			return MAX_ROWS_DISPLAY_SIZE_TEXTAREA;
		return PresentationUtils.getDisplayRows(column);
	}
	
	public static String getConverter(Column column) {
		if ("BLOB".equals(column.getType())
			|| "BINARY".equals(column.getType())) {
			return "getBytes";
		}
		
		String javaType = ConvertUtils.getJavaTypeClassFromDBType(column);
		if (ConvertUtils.JAVA_STRING.equals(javaType)) {
			return "getString";
		}
		if (ConvertUtils.JAVA_BIGINTEGER_TYPE.equals(javaType)) {
			return "getBigInteger";
		}
		if (ConvertUtils.JAVA_BIGDECIMAL_TYPE.equals(javaType)) {
			return "getBigDecimal";
		}
		
		if (ConvertUtils.JAVA_BOOLEAN_TYPE.equals(javaType)
			|| ConvertUtils.JAVA_BOOLEAN.equals(javaType)
			) {
			return "getBoolean";
		}
		
		if (ConvertUtils.JAVA_SHORT_TYPE.equals(javaType)			
			|| ConvertUtils.JAVA_SHORT.equals(javaType)
			) {
			return "getShort";
		}
		if (ConvertUtils.JAVA_LONG_TYPE.equals(javaType)			
			|| ConvertUtils.JAVA_LONG.equals(javaType)
			) {
			return "getLong";
		}
		if (ConvertUtils.JAVA_DOUBLE_TYPE.equals(javaType)			
			|| ConvertUtils.JAVA_DOUBLE.equals(javaType)
			) {
			return "getDouble";
		}
		if (ConvertUtils.JAVA_INTEGER_TYPE.equals(javaType)
			|| ConvertUtils.JAVA_INTEGER.equals(javaType)
				) {
			return "getInteger";
		}
		if (ConvertUtils.JAVA_DATE_TYPE.equals(javaType)) {
			return "getDate";
		}
		if (ConvertUtils.JAVA_TIME_TYPE.equals(javaType)) {
			return "getDate";
		}
		if (ConvertUtils.JAVA_TIMESTAMP_TYPE.equals(javaType)) {
			return "getDate";
		}
		if (ConvertUtils.JAVA_CLOB_TYPE.equals(javaType)) {
			return "getString";
		}
		if (ConvertUtils.JAVA_BLOB_TYPE.equals(javaType)
			|| ConvertUtils.JAVA_BLOB.equals(javaType)) {
			return "getBytes";
		}
		return "get"+ConvertUtils.getJavaTypeFromDBTypeOnly(column);
	}
	
	public boolean isVaadinApplication (Table table) {
		return (!table.isManyToMany())?true:false;
	}
}
