package com.smm.bartender.controller.requests;

import org.springframework.stereotype.Component;

@Component
public class ConfigurationRequest implements IRequest {

    private String propertyName;
    private Integer propertyValue;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Integer getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(Integer propertyValue) {
        this.propertyValue = propertyValue;
    }

}
