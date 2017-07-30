package com.smm.bartender.services;

import com.smm.bartender.controller.requests.IRequest;
import com.smm.bartender.controller.responses.IResponse;
import com.smm.bartender.exception.BarTenderException;

public interface IBarTenderService {

    public IResponse processRequest(IRequest request, String operationType) throws BarTenderException;

    void validateRequest(IRequest request) throws BarTenderException;

    IResponse performOperations(IRequest request, String operationType) throws BarTenderException;
}
