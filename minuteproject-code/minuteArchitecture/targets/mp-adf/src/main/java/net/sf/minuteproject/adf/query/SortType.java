package net.sf.minuteproject.adf.query;

public enum SortType {

    ASC,
    DESC;

    public String value() {
        return name();
    }

    public static SortType fromValue(String v) {
        return valueOf(v);
    }

}