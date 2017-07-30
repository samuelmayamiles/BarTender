package com.smm.bartender.controller.responses;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigurationResponse implements IResponse {

    private String propertyName;
    private Integer propertyValue;

    public ConfigurationResponse() {}

    public ConfigurationResponse(String propertyName, Integer propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

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
