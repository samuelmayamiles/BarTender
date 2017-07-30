package com.smm.bartender.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smm.bartender.configuration.BarTenderConstants;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    private Integer id;
    private String status = BarTenderConstants.ORDER_STATUS_REQUESTED;
    private Map<EDrink, Integer> drinksOrdered = new HashMap<EDrink, Integer>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<EDrink, Integer> getDrinksOrdered() {
        return drinksOrdered;
    }

    public void setDrinksOrdered(Map<EDrink, Integer> drinksOrdered) {
        this.drinksOrdered = drinksOrdered;
    }
}
