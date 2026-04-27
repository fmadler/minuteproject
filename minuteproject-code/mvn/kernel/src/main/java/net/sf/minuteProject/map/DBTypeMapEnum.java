package net.sf.minuteProject.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Types;
import java.util.Arrays;

import static net.sf.minuteProject.map.DBConstant.*;

@AllArgsConstructor
@Getter
public enum DBTypeMapEnum {

    STRING ("String", JAVA_LANG, Types.VARCHAR),
    INTEGER ("Integer", JAVA_LANG, Types.INTEGER),
    BIGINT ("Long", JAVA_LANG, Types.BIGINT),
    DOUBLE ("Double", JAVA_LANG, Types.DOUBLE),
    BOOLEAN ("Boolean", JAVA_LANG, Types.BOOLEAN),
    DATE ("Date", JAVA_UTIL, Types.DATE),
    TIMESTAMP ("Timestamp", JAVA_UTIL, Types.TIMESTAMP);

    private final String className;
    private final String packageName;
    private final int type;

    public static DBTypeMapEnum of (int type) {
        return Arrays.asList(DBTypeMapEnum.values()).stream()
                .filter(dbTypeMapEnum -> dbTypeMapEnum.getType() == type)
                .findAny().orElse(DBTypeMapEnum.STRING);
    }
}
