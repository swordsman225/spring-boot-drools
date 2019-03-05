package com.huawei.hicloud.controller;

import com.huawei.hicloud.service.IDroolsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/drools")
public class DroolsController {

    private static final Logger log = LoggerFactory.getLogger(DroolsController.class);

    @Autowired
    private IDroolsService iDroolsService;


    @RequestMapping(value = "/execute", method = RequestMethod.GET)
    public Map<String, Object> execute() {
        HashMap<String, Object> result = new HashMap<>();

        iDroolsService.execute();

        result.put("code", "200");
        result.put("msg", "Drools execute sucess!");

        return result;
    }


    @RequestMapping(value = "/loadRules", method = RequestMethod.POST)
    public Map<String, Object> load() {
        HashMap<String, Object> result = new HashMap<>();

        iDroolsService.loadRules();

        result.put("code", "200");
        result.put("msg", "Drools execute sucess!");

        return result;
    }

}
