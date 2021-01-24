package com.omnirio.account.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDetail extends AccountRequest {
    private UserResponse customer;


    public AccountDetail() {
    }

    public UserResponse getCustomer() {
        return customer;
    }

    public void setCustomer(UserResponse customer) {
        this.customer = customer;
    }
}
