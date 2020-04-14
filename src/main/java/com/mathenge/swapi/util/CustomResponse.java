package com.mathenge.swapi.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CustomResponse {

    private int status;
    private String message = "";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> data;

    public CustomResponse(final int status, final String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public CustomResponse(final int status, final String message, final Map<String, Object> data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
