package com.smm.bartender.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.smm.bartender.configuration.BarTenderConstants;
import com.smm.bartender.controller.requests.IRequest;
import com.smm.bartender.controller.requests.OrderRequest;
import com.smm.bartender.controller.responses.IResponse;
import com.smm.bartender.exception.BarTenderException;
import com.smm.bartender.model.Customer;
import com.smm.bartender.model.CustomerOrder;
import com.smm.bartender.model.EDrink;
import com.smm.bartender.model.Order;

@Service
public class OrderService implements IBarTenderService {

    public static final Logger LOGGER = Logger.getLogger(OrderService.class);

    public static Map<Integer, Customer> workLoad = Collections.synchronizedMap(new HashMap<Integer, Customer>());
    public static Integer secondsPerDrink = 5;
    public static Integer beersAtATime = 2;

    private boolean busy = false;
    private Integer currentOrderNumber = 1;

    @Autowired
    private Environment env;

    @Override
    public IResponse processRequest(IRequest request, String operationType) throws BarTenderException {
        validateRequest(request);
        return performOperations(request, operationType);
    }

    @Override
    public void validateRequest(IRequest request) throws BarTenderException {
        OrderRequest orderRequest = (OrderRequest) request;

        if (orderRequest == null) {
            throw new BarTenderException(BarTenderException.CODE_400_VALUE);
        }

        if (orderRequest.getCustomerId() == null || orderRequest.getCustomerId() <= 0) {
            throw new BarTenderException(BarTenderException.CODE_400_VALUE);
        }

        if (orderRequest.getDrinksOrdered() == null || orderRequest.getDrinksOrdered().isEmpty()) {
            throw new BarTenderException(BarTenderException.CODE_400_VALUE);
        }

        for (CustomerOrder customerOrder : orderRequest.getDrinksOrdered()) {
            boolean foundType = false;

            if (customerOrder.getAmount() == null || customerOrder.getAmount() < 0) {
                throw new BarTenderException(BarTenderException.CODE_400_VALUE);
            }

            for (EDrink drinkType : EDrink.values()) {
                if (drinkType.equals(customerOrder.getDrinkType())) {
                    foundType = true;
                    break;
                }
            }

            if (!foundType) {
                throw new BarTenderException(BarTenderException.CODE_400_VALUE);
            }
        }
    }

    @Override
    public IResponse performOperations(IRequest request, String operationType) throws BarTenderException {
        OrderRequest orderRequest = (OrderRequest) request;
        Integer customerId = orderRequest.getCustomerId();

        if (!workLoad.containsKey(orderRequest.getCustomerId())) {
            workLoad.put(customerId, new Customer(customerId));
        }
        Customer customer = workLoad.get(customerId);

        Order newOrder = new Order();
        newOrder.setId(currentOrderNumber++);
        newOrder.setDrinksOrdered(parseDrinks(orderRequest.getDrinksOrdered()));

        customer.getOrders().add(newOrder);

        if (busy) {
            newOrder.setStatus(BarTenderConstants.ORDER_STATUS_REJECTED);
            LOGGER.info("BarTender is busy and order " + newOrder.getId() + " was rejected.");
            throw new BarTenderException(BarTenderException.CODE_429_VALUE);
        } else {
            processOrder(customer.getCustomerId(), newOrder);
        }

        return null;
    }

    private Map<EDrink, Integer> parseDrinks(List<CustomerOrder> drinksOrdered) {
        Map<EDrink, Integer> orderDrinks = new HashMap<EDrink, Integer>();

        for (CustomerOrder customerOrder : drinksOrdered) {
            EDrink drinkType = customerOrder.getDrinkType();

            if (!orderDrinks.containsKey(drinkType)) {
                orderDrinks.put(customerOrder.getDrinkType(), 0);
            }
            orderDrinks.put(drinkType, orderDrinks.get(drinkType) + customerOrder.getAmount());
        }

        return orderDrinks;
    }

    private void processOrder(Integer customerId, Order newOrder) {
        busy = true;
        Timer timerRate = new Timer(true);
        Integer secondsToProcess = calculateServingTime(newOrder);
        Integer ticksTime = secondsPerDrink;

        newOrder.setStatus(BarTenderConstants.ORDER_STATUS_IN_PREPARATION);

        TimerTask timer = new TimerTask() {
            Integer tickCounter = -ticksTime;

            public void run() {
                tickCounter += ticksTime;

                if (tickCounter == secondsToProcess) {
                    newOrder.setStatus(BarTenderConstants.ORDER_STATUS_SERVED);
                    LOGGER.info("BarTender has finished processing order " + newOrder.getId());
                    busy = false;
                    timerRate.cancel();
                } else {
                    LOGGER.info("BarTender " + (tickCounter == 0 ? "has started" : "is still") + " processing order "
                            + newOrder.getId());
                }
            }
        };
        timerRate.scheduleAtFixedRate(timer, 0, ticksTime * 1000);
    }

    private Integer calculateServingTime(Order newOrder) {
        Integer processingTime = 0;
        Integer beers = newOrder.getDrinksOrdered().get(EDrink.BEER);
        Integer drinks = newOrder.getDrinksOrdered().get(EDrink.DRINK);

        if (beers != null) {
            Integer joinedBeers = beers / beersAtATime;
            processingTime += (joinedBeers + (beers - (joinedBeers * beersAtATime))) * secondsPerDrink;
        }
        if (drinks != null) {
            processingTime += drinks * secondsPerDrink;
        }

        return processingTime;
    }
}
