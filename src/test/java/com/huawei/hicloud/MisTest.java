package com.huawei.hicloud;

import com.huawei.hicloud.mode.vo.NetworkVO;
import com.huawei.hicloud.mode.vo.SubnetVO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

public class MisTest {

    private static final Logger logger = LoggerFactory.getLogger(MisTest.class);

    @Test
    public void test01() {

        String cidr = null;

        NetworkVO networkVO = new NetworkVO();
        networkVO.setId("123");
        networkVO.setId("net");
        SubnetVO sub = new SubnetVO();
        sub.setId("321");
        sub.setName("subnet");
        sub.setCidr("192.168.10.0/24");
        networkVO.setSubnetVO(null);

        cidr = Optional.ofNullable(networkVO).map(n -> n.getSubnetVO()).map(s -> s.getCidr()).orElse("");

        logger.info("cidr: {}.", cidr);

    }

    @Test
    public void test02() {

        ArrayList<String> strList = new ArrayList<>();
        strList.add("123");

        logger.info("result: {}.", strList.contains(null));

    }




}
