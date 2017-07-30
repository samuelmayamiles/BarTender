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

import com.smm.bartender.controller.requests.ConfigurationRequest;
import com.smm.bartender.controller.responses.IResponse;
import com.smm.bartender.exception.BarTenderException;
import com.smm.bartender.services.ConfigurationService;

@RestController
@RequestMapping("/configure")
@Validated
public class ConfigurationController extends AbstractController {

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Change property value.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Value changed succesfully."),
            @ApiResponse(code = 400, message = "Bad Request parameters."),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<IResponse> handleRequest(@RequestBody @Valid ConfigurationRequest request) {

        try {
            return new ResponseEntity<IResponse>(configurationService.processRequest(request, null), HttpStatus.OK);
        } catch (BarTenderException e) {
            return processError(e);
        }
    }
}
