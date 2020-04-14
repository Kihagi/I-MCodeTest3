package com.mathenge.swapi.util;

import java.util.List;

public class CustomResponseWithList {

    private int status;
    private String message = "";
    private List<Object> data;

    public CustomResponseWithList(final int status, final String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public CustomResponseWithList(final int status, final String message, final List<Object> data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
