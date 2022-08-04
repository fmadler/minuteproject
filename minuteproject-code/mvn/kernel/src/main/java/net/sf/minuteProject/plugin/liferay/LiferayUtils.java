package net.sf.minuteProject.plugin.liferay;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.DatabaseUtils;

public class LiferayUtils {

	public static String getSbPrimaryKeyType(Column column) {
		Table table = column.getTable();
		if (ColumnUtils.isNaturalPk(column))
			return "";
		//strip auto in autoincrement
		String strategyLowercase = getStrategyLowercase(table);
		return (strategyLowercase.equals("autoincrement"))?"increment":strategyLowercase;
	}
	
	private static String getStrategyLowercase(Table table) {
		return DatabaseUtils.getPrimaryKeyPolicyPatternEnum(table).toString().toLowerCase();
	}
	/*
	 * Info deduced from liferay sdk eclipse plugin
	 */
	public static String getSbColumnType(Column column) {
		if (ColumnUtils.isString(column))
			return "String";
		if (ColumnUtils.isTime(column))
			return "Date";
		if (ColumnUtils.isInteger(column))
			return "int";
		if (ColumnUtils.isLong(column))
			return "long";
		if (ColumnUtils.isDouble(column))
			return "double";
		if (ColumnUtils.isBoolean(column))
			return "boolean";
		//do clob and glob
		return "String";
	}
}
