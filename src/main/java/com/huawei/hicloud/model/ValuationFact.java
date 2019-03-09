package com.huawei.hicloud.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ValuationFact {

    private Integer price;

    private Customer customer;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
