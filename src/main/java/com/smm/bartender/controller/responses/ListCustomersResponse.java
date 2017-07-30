package com.smm.bartender.controller.responses;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smm.bartender.model.Customer;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListCustomersResponse implements IResponse {

    private Map<Integer, Customer> customers = new HashMap<Integer, Customer>();

    public Map<Integer, Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Map<Integer, Customer> customers) {
        this.customers = customers;
    }
}
