package com.smm.bartender.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smm.bartender.configuration.BarTenderConstants;
import com.smm.bartender.controller.responses.IResponse;
import com.smm.bartender.controller.responses.ListCustomersResponse;
import com.smm.bartender.controller.responses.ListOrdersResponse;
import com.smm.bartender.exception.BarTenderException;
import com.smm.bartender.services.ListService;

@RestController
@RequestMapping("/list")
@Validated
public class ListController extends AbstractController {

    @Autowired
    private ListService listService;

    @RequestMapping(method = RequestMethod.GET, value = "/Orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List orders", response = ListOrdersResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List retrieved succesfully"),
            @ApiResponse(code = 404, message = "List type not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<IResponse> listOrders() {

        try {
            return new ResponseEntity<IResponse>(listService.processRequest(null, BarTenderConstants.OPERATION_LIST_ORDERS), HttpStatus.OK);
        } catch (BarTenderException e) {
            return processError(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List customers", response = ListCustomersResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List retrieved succesfully"),
            @ApiResponse(code = 404, message = "List type not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<IResponse> listCustomers() {

        try {
            return new ResponseEntity<IResponse>(listService.processRequest(null, BarTenderConstants.OPERATION_LIST_CUSTOMERS), HttpStatus.OK);
        } catch (BarTenderException e) {
            return processError(e);
        }
    }
}
