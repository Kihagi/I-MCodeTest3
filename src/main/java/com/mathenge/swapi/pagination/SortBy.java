package com.mathenge.swapi.pagination;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathenge on 4/14/2020
 */
public class SortBy {

    private Map<String, SortOrder> mapOfSorts;

    public SortBy() {
        if(null == mapOfSorts) {
            mapOfSorts = new HashMap<String, SortOrder>();
        }
    }

    public Map<String, SortOrder>  getSortBys() {
        return mapOfSorts;
    }

    public void addSort(String sortBy) {
        mapOfSorts.put(sortBy, SortOrder.ASC);
    }

    public void addSort(String sortBy, SortOrder sortOrder) {
        mapOfSorts.put(sortBy, sortOrder);
    }
}
