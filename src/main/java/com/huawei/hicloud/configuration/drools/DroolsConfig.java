package com.huawei.hicloud.configuration.drools;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {

    @Bean
    public KieBase kieBase() {
        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();

        return kBase;
    }

}
