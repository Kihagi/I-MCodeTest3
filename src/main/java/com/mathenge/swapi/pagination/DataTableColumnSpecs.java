package com.mathenge.swapi.pagination;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mathenge on 4/14/2020
 */
@Getter
@Setter
public class DataTableColumnSpecs {

    private int index;

    private String data;

    private String name;

    private boolean searchable;

    private boolean orderable;

    private String search;

    private boolean regex;

    private String sortDir;

    public DataTableColumnSpecs(HttpServletRequest request, int i) {
        this.setIndex(i);
        prepareColumnSpecs(request, i);
    }

    private void prepareColumnSpecs(HttpServletRequest request, int i) {
        this.setData(request.getParameter("columns["+ i +"][data]"));
        this.setName(request.getParameter("columns["+ i +"][name]"));
        this.setOrderable(Boolean.valueOf(request.getParameter("columns["+ i +"][orderable]")));
        this.setRegex(Boolean.valueOf(request.getParameter("columns["+ i +"][search][regex]")));
        this.setSearch(request.getParameter("columns["+ i +"][search][value]"));
        this.setSearchable(Boolean.valueOf(request.getParameter("columns["+ i +"][searchable]")));

        int sortableCol = Integer.parseInt(request.getParameter("order[0][column]"));
        String sortDir = request.getParameter("order[0][dir]");

        if(i == sortableCol) {
            this.setSortDir(sortDir);
        }
    }
}
