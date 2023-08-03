package net.sf.minuteProject.plugin.typescript;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;

public class TypescriptUtils {

    public static String convertColumnType(Column column) {
        if (ColumnUtils.isBoolean(column)) {
            return "boolean";
        } else if (ColumnUtils.isInteger(column)) {
            return "int";
        } else if (ColumnUtils.isDouble(column)) {
            return "number";
        } else if (ColumnUtils.isTimeStampColumn(column)) {
            return "timestamp";
        } else if (ColumnUtils.isTimeColumn(column)) {
            return "date";
        }
        return "string";
    }
}
