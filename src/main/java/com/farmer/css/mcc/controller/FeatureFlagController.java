package com.farmer.css.mcc.controller;

import com.farmer.css.mcc.service.FeatureFlagService;
import com.farmer.css.redisConnection.model.FeatureFlag;
import com.farmer.css.redisConnection.model.TransactionNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class FeatureFlagController {

    @Autowired
    private FeatureFlagService featureFlagService;
    private ResponseHandler responseHandler = new ResponseHandler();


    @PostMapping
    @RequestMapping("/setFeatureFlag")
    public TransactionNotification setConfiguration (@RequestBody FeatureFlag featureFlag){
        try {
            return featureFlagService.setConfigurationProperties(featureFlag.getFeatureFlagName(), featureFlag.getFeatureFlagValue());
            
        }catch(Exception exception){
            responseHandler.constructGenericExceptionResponse(exception.getMessage());
        }
        return null;
    }

    @GetMapping("/getAllFeatureFlags")
    public List<FeatureFlag> getConfiguration () {
        List<FeatureFlag> valueAsBytes = featureFlagService.getConfigurationProperties();
        System.out.println(valueAsBytes);
        return valueAsBytes;
    }

    @GetMapping(value = "/test")
    public String test(){
        return "Working";
    }

}