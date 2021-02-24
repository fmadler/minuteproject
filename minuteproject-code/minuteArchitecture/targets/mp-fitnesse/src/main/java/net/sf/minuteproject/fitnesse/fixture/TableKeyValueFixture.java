package net.sf.minuteproject.fitnesse.fixture;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.minuteproject.utils.query.QueryUtils;

public abstract class TableKeyValueFixture extends DbSddFixture{

    public static Map<String, String> getKeyValueStringMap(String tableName, String keyColumn, String valueColumn) throws SQLException{
        // select key value column
        Map<String, String> map = new HashMap<String, String>();
        String query = "select "+ keyColumn + " , "+ valueColumn +" from "+tableName;
        Object[][] table = QueryUtils.executeQuery(query);
        for (int i = 0; i < table.length; i++) {
            Object [] row = table[i];
            map.put(row[0].toString(), row[1].toString());
        }
        return map;
    }
}
