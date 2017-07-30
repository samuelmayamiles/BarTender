package com.smm.bartender.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smm.bartender.controller.requests.OrderRequest;
import com.smm.bartender.controller.responses.IResponse;
import com.smm.bartender.exception.BarTenderException;
import com.smm.bartender.services.OrderService;

@RestController
@RequestMapping("/order")
@Validated
public class OrderController extends AbstractController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Place Order")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Order acknowledged and will be served when ready."),
            @ApiResponse(code = 400, message = "Bad Request parameters."),
            @ApiResponse(code = 429, message = "Order cannot be accepted at this moment."),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<IResponse> handleRequest(@RequestBody @Valid OrderRequest request) {

        try {
            return new ResponseEntity<IResponse>(orderService.processRequest(request, null), HttpStatus.OK);
        } catch (BarTenderException e) {
            return processError(e);
        }
    }
}
