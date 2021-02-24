package net.sf.minuteproject.adf.query;

public enum UnaryOperationCode {
    
    EQUALS,
    NOT_EQUALS,
    GREATER_THAN,
    GREATER_THAN_EQUALS,
    LESS_THAN,
    LESS_THAN_EQUALS,
    CONTAINS,
    DOES_NOT_CONTAIN,
    LIKE,
    NOT_LIKE,
    LIKE_IGNORE_CASE,
    NOT_LIKE_IGNORE_CASE,
    IS_BLANK,
    IS_NOT_BLANK;

    public String value() {
        return name();
    }

    public static UnaryOperationCode fromValue(String v) {
        return valueOf(v);
    }
}
