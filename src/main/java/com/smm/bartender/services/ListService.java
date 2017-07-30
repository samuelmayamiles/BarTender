package com.smm.bartender.services;

import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.smm.bartender.configuration.BarTenderConstants;
import com.smm.bartender.controller.requests.IRequest;
import com.smm.bartender.controller.responses.IResponse;
import com.smm.bartender.controller.responses.ListCustomersResponse;
import com.smm.bartender.controller.responses.ListOrdersResponse;
import com.smm.bartender.exception.BarTenderException;
import com.smm.bartender.model.Customer;
import com.smm.bartender.model.Order;

@Service
public class ListService implements IBarTenderService {

    @Override
    public IResponse processRequest(IRequest request, String operationType) throws BarTenderException {
        validateRequest(request);
        return performOperations(request, operationType);
    }

    @Override
    public void validateRequest(IRequest request) throws BarTenderException {}

    @Override
    public IResponse performOperations(IRequest request, String operationType) throws BarTenderException {
        IResponse response = null;

        switch (operationType) {
            case BarTenderConstants.OPERATION_LIST_ORDERS:
                response = generateOrdersList();
                break;
            case BarTenderConstants.OPERATION_LIST_CUSTOMERS:
                response = generateCustomersList();
                break;
            default:
                throw new BarTenderException(BarTenderException.CODE_404_VALUE);
        }

        return response;
    }

    private IResponse generateOrdersList() {
        ListOrdersResponse ordersList = new ListOrdersResponse();
        Integer addOrder = 0;

        for (Entry<Integer, Customer> workLoadEntry : OrderService.workLoad.entrySet()) {
            for (Order order : workLoadEntry.getValue().getOrders()) {
                addOrder = 1;

                switch (order.getStatus()) {
                    case BarTenderConstants.ORDER_STATUS_SERVED:
                        ordersList.getOrdersServed().add(order);
                        break;
                    case BarTenderConstants.ORDER_STATUS_IN_PREPARATION:
                        ordersList.getOrdersInPreparation().add(order);
                        break;
                    case BarTenderConstants.ORDER_STATUS_REJECTED:
                        ordersList.getOrdersRejected().add(order);
                        break;
                    default:
                        addOrder = 0;
                        break;
                }

                ordersList.setTotalOrders(ordersList.getTotalOrders() + addOrder);
            }
        }

        return ordersList;
    }

    private IResponse generateCustomersList() {
        ListCustomersResponse customersList = new ListCustomersResponse();

        customersList.getCustomers().putAll(OrderService.workLoad);

        return customersList;
    }
}
