package com.mathenge.swapi.pagination;

/**
 * @author Mathenge on 4/14/2020
 */
public enum SortOrder {

    ASC("ASC"),
    DESC("DESC");

    private final String value;

    SortOrder(String v) {
        value = v;
    }

    public static SortOrder fromValue(String v) {
        for (SortOrder c : SortOrder.values()) {
            if (c.name().equalsIgnoreCase(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public String value() {
        return value;
    }
}
