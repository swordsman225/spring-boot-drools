package com.huawei.hicloud;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
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

        String ruleFilePath = "classpath:valuation2.drl";

        String ruleContent = this.getFileContent(ruleFilePath);

        KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kb.add(ResourceFactory.newByteArrayResource(ruleContent.getBytes("utf-8")), ResourceType.DRL);
        kb.add(ResourceFactory.newByteArrayResource(ruleContent.getBytes("utf-8")), ResourceType.DRL);

        KnowledgeBuilderErrors errors = kb.getErrors();
        for (KnowledgeBuilderError error : errors) {
            log.error(error.toString());
        }

        InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addPackages(kb.getKnowledgePackages());

        KieSession kSession = kBase.newKieSession();

        kSession.fireAllRules();

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
