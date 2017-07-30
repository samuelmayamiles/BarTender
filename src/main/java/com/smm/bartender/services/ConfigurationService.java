package com.smm.bartender.services;

import org.springframework.stereotype.Service;

import com.smm.bartender.controller.requests.ConfigurationRequest;
import com.smm.bartender.controller.requests.IRequest;
import com.smm.bartender.controller.responses.ConfigurationResponse;
import com.smm.bartender.controller.responses.IResponse;
import com.smm.bartender.exception.BarTenderException;

@Service
public class ConfigurationService implements IBarTenderService {

    @Override
    public IResponse processRequest(IRequest request, String operationType) throws BarTenderException {
        validateRequest(request);
        return performOperations(request, operationType);
    }

    @Override
    public void validateRequest(IRequest request) throws BarTenderException {
        ConfigurationRequest configurationRequest = (ConfigurationRequest) request;

        if (configurationRequest == null) {
            throw new BarTenderException(BarTenderException.CODE_400_VALUE);
        }

        if (configurationRequest.getPropertyName() == null || configurationRequest.getPropertyName().isEmpty()) {
            throw new BarTenderException(BarTenderException.CODE_400_VALUE);
        }

        if (configurationRequest.getPropertyValue() == null || configurationRequest.getPropertyValue() <= 0) {
            throw new BarTenderException(BarTenderException.CODE_400_VALUE);
        }
    }

    @Override
    public IResponse performOperations(IRequest request, String operationType) throws BarTenderException {
        ConfigurationRequest configurationRequest = (ConfigurationRequest) request;
        String propertyName = configurationRequest.getPropertyName().toLowerCase();

        switch (propertyName) {
            case "secondsperdrink":
                OrderService.secondsPerDrink = configurationRequest.getPropertyValue();
                break;
            case "beersatatime":
                OrderService.beersAtATime = configurationRequest.getPropertyValue();
                break;
            default:
                throw new BarTenderException(BarTenderException.CODE_400_VALUE);
        }

        return new ConfigurationResponse(configurationRequest.getPropertyName(), configurationRequest.getPropertyValue());
    }
}
