package com.smm.bartender.controller.responses;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.smm.bartender.exception.BarTenderException;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements IResponse {

    private Integer status;
    private Integer errorCode;
    private String message;
    @JsonIgnore
    private HttpStatus httpStatus;

    public ErrorResponse() {}

    public ErrorResponse(BarTenderException exception) {
        this.status = -1;
        this.errorCode = exception.getErrorCode();
        this.message = exception.getMessage();
        this.httpStatus = selectHttpStatus(exception.getErrorCode());
    }

    private HttpStatus selectHttpStatus(Integer errorCode) {
        switch (errorCode) {
            case 400:
                return HttpStatus.BAD_REQUEST;
            case 404:
                return HttpStatus.NOT_FOUND;
            case 429:
                return HttpStatus.TOO_MANY_REQUESTS;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
