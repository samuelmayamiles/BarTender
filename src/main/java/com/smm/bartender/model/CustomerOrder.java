package com.smm.bartender.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerOrder {

    private EDrink drinkType;
    private Integer amount = 0;

    public EDrink getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(EDrink drinkType) {
        this.drinkType = drinkType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
