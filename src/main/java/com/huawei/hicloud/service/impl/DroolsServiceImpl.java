package com.huawei.hicloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.huawei.hicloud.model.ValuationFact;
import com.huawei.hicloud.service.IDroolsService;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

@Service
public class DroolsServiceImpl implements IDroolsService {

    private static final Logger log = LoggerFactory.getLogger(DroolsServiceImpl.class);

    @Autowired
    private KieBase kieBase;

    @Override
    public void loadRules() {
        String ruleFilePath = "classpath:rules/valuation1.drl";
        String ruleContent = this.getFileContent(ruleFilePath);

        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        try {
            kBuilder.add(ResourceFactory.newByteArrayResource(ruleContent.getBytes("utf-8")), ResourceType.DRL);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //kBuilder.add(ResourceFactory.newClassPathResource("changeset.xml", DroolsTest.class), ResourceType.CHANGE_SET);


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

        kieBase = kBase;
    }


    @Override
    public void execute() {
        KieSession kSession = kieBase.newKieSession();

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
