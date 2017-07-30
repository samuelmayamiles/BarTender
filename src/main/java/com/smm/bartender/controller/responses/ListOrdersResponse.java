package com.smm.bartender.controller.responses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smm.bartender.model.Order;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListOrdersResponse implements IResponse {

    private List<Order> ordersServed = new ArrayList<Order>();
    private List<Order> ordersInPreparation = new ArrayList<Order>();
    private List<Order> ordersRejected = new ArrayList<Order>();
    private Integer totalOrders = 0;

    public List<Order> getOrdersServed() {
        return ordersServed;
    }

    public void setOrdersServed(List<Order> ordersServed) {
        this.ordersServed = ordersServed;
    }

    public List<Order> getOrdersInPreparation() {
        return ordersInPreparation;
    }

    public void setOrdersInPreparation(List<Order> ordersInPreparation) {
        this.ordersInPreparation = ordersInPreparation;
    }

    public List<Order> getOrdersRejected() {
        return ordersRejected;
    }

    public void setOrdersRejected(List<Order> ordersRejected) {
        this.ordersRejected = ordersRejected;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }
}
