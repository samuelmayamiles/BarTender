package com.smm.bartender.controller.requests;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smm.bartender.model.CustomerOrder;

@Component
public class OrderRequest implements IRequest {

    private Integer customerId;
    private List<CustomerOrder> drinksOrdered = new ArrayList<CustomerOrder>();

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<CustomerOrder> getDrinksOrdered() {
        return drinksOrdered;
    }

    public void setDrinksOrdered(List<CustomerOrder> drinksOrdered) {
        this.drinksOrdered = drinksOrdered;
    }

}
