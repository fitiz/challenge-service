package com.fitiz.challenge.exception.exceptionhandler;

import java.util.List;

public class ServiceErrorResponse {

    List<ServiceError> errors;

    public ServiceErrorResponse() {
    }

    public ServiceErrorResponse(List<ServiceError> errors) {
        this.errors = errors;
    }

    public List<ServiceError> getErrors() {
        return errors;
    }
}
