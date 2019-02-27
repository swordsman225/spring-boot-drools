package com.huawei.hicloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DroolsApplication {

    private static final Logger log = LoggerFactory.getLogger(DroolsApplication.class);

    public static void main(String[] args) {
        log.info("### Drools application start ...");
        SpringApplication.run(DroolsApplication.class, args);
    }

}
