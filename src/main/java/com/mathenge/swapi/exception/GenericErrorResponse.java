package com.mathenge.swapi.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenericErrorResponse {

    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Object> errors;

    public GenericErrorResponse(final int status, final String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public GenericErrorResponse(final int status, final String message, final List<Object> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
