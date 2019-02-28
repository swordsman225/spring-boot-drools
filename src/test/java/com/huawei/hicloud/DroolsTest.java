package com.huawei.hicloud;

import com.alibaba.fastjson.JSON;
import com.huawei.hicloud.model.ValuationFact;
import org.drools.core.base.RuleNameEndsWithAgendaFilter;
import org.drools.core.base.RuleNameMatchesAgendaFilter;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;

public class DroolsTest {

    private static final Logger log = LoggerFactory.getLogger(DroolsApplication.class);

    @Test
    public void test01() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("ksession_3");

        int count = kieSession.fireAllRules();

        log.info("Execute {} rules!", count);
    }

    @Test
    public void test02() throws Exception {
        String ruleFilePath1 = "classpath:rules/valuation1.drl";
        String ruleFilePath2 = "classpath:valuation2.drl";

        String ruleContent1 = this.getFileContent(ruleFilePath1);
        String ruleContent2 = this.getFileContent(ruleFilePath2);

        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kBuilder.add(ResourceFactory.newByteArrayResource(ruleContent1.getBytes("utf-8")), ResourceType.DRL);
        kBuilder.add(ResourceFactory.newByteArrayResource(ruleContent2.getBytes("utf-8")), ResourceType.DRL);

        KnowledgeBuilderErrors errors = kBuilder.getErrors();
        boolean hasError = false;
        for (KnowledgeBuilderError error : errors) {
            hasError = true;
            log.error("### " + error.toString());
        }
        if (hasError) {
            log.error("Knowledge builder error!");
            return;
        }

        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addPackages(kBuilder.getKnowledgePackages());

        KieSession kSession = kBase.newKieSession();

        int count = kSession.fireAllRules(new RuleNameMatchesAgendaFilter("rule_.*"));
        log.info("Execute {} rules!", count);
        kSession.dispose();
    }




    @Test
    public void test03() throws Exception {
        String ruleFilePath = "classpath:rules/valuation.drl";
        String ruleContent = this.getFileContent(ruleFilePath);

        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kBuilder.add(ResourceFactory.newByteArrayResource(ruleContent.getBytes("utf-8")), ResourceType.DRL);

        KnowledgeBuilderErrors errors = kBuilder.getErrors();
        boolean hasError = false;
        for (KnowledgeBuilderError error : errors) {
            hasError = true;
            log.error("### " + error.toString());
        }
        if (hasError) {
            log.error("### Knowledge builder error!");
            return;
        }

        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addPackages(kBuilder.getKnowledgePackages());


        KieSession kSession = kBase.newKieSession();

        ValuationFact fact = new ValuationFact();
        fact.setPrice(200);

        kSession.insert(fact);

        int count = kSession.fireAllRules();
        log.info("Execute {} rules!", count);

        log.info("Rule engin execute result: {}.", JSON.toJSONString(fact));

        kSession.dispose();
    }



    public String getFileContent(String filePath) {
        String content = null;
        if (ResourceUtils.isUrl(filePath)) {
            try {
                File file = ResourceUtils.getFile(filePath);
                FileInputStream fis = new FileInputStream(file);

                byte[] fileContent = new byte[fis.available()];
                fis.read(fileContent);

                content = new String(fileContent, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("Rule file content: {}.", content);

        return content;
    }

}
