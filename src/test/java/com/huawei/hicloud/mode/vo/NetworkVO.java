package com.huawei.hicloud.mode.vo;

public class NetworkVO {

    private String id;

    private String name;

    private SubnetVO subnetVO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubnetVO getSubnetVO() {
        return subnetVO;
    }

    public void setSubnetVO(SubnetVO subnetVO) {
        this.subnetVO = subnetVO;
    }
}
