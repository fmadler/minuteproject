package net.sf.minuteProject.plugin.typescript;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;

public class TypescriptUtils {

    public static String convertColumnType(Column column) {
        if (ColumnUtils.isBoolean(column)) {
            return "boolean";
        } else if (ColumnUtils.isNumeric(column)) {
            return column.isRequired() ? "number":"any";
        } else if (ColumnUtils.isTimeStampColumn(column)) {
            return "Date";
        } else if (ColumnUtils.isTimeColumn(column)) {
            return "Date";
        }
        return "string";
    }
}
