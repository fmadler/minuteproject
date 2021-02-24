package net.sf.minuteproject.adf.query;

public enum LogicalOperationCode {

    AND,
    OR;

    public String value() {
        return name();
    }

    public static LogicalOperationCode fromValue(String v) {
        return valueOf(v);
    }

}

