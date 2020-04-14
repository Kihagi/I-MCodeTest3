package com.mathenge.swapi.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenericRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Object> errors;

    public GenericRuntimeException(final int status, final String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public GenericRuntimeException(final int status, final String message, final List<Object> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
