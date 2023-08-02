package net.sf.minuteProject.plugin.graphql;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.ColumnUtils;

public class GraphQLUtils {

    public static String convertColumnType(Column column) {
        if (ColumnUtils.isBoolean(column)) {
            return "Boolean";
        } else if (ColumnUtils.isInteger(column)) {
            return "Int";
        } else if (ColumnUtils.isDouble(column)) {
            return "Float";
        } else if (ColumnUtils.isTimeStampColumn(column)) {
            return "Timestamp";
        } else if (ColumnUtils.isTimeColumn(column)) {
            return "Date";
        }
        return "String";
    }

    public static boolean hasMutation(Model model) {
        return model.getStatementModel().getSddPackage().getOrderedPackages()
                .stream()
                .flatMap(u->u.getPureQueries(true).stream())
                .count()>0;
    }
}
