package com.mathenge.swapi.pagination;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Mathenge on 4/14/2020
 */
public class DataTableResults<T> {

    private String draw;

    private String recordsFiltered;

    private String recordsTotal;

    /** The list of data objects. */
    @SerializedName("data")
    List<T> listOfDataObjects;

    public String getJson() {
        return new Gson().toJson(this);
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(String recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public String getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(String recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<T> getListOfDataObjects() {
        return listOfDataObjects;
    }

    public void setListOfDataObjects(List<T> listOfDataObjects) {
        this.listOfDataObjects = listOfDataObjects;
    }
}
