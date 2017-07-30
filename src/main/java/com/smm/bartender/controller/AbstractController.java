package com.smm.bartender.controller;

import org.springframework.http.ResponseEntity;

import com.smm.bartender.controller.responses.ErrorResponse;
import com.smm.bartender.controller.responses.IResponse;
import com.smm.bartender.exception.BarTenderException;

public abstract class AbstractController {

    protected static final ResponseEntity<IResponse> processError(BarTenderException exception) {
        ErrorResponse response = new ErrorResponse(exception);
        return new ResponseEntity<IResponse>(response, response.getHttpStatus());
    }
}
