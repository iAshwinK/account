package com.omnirio.account.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.omnirio.account.vo.AccountType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "AccountEntity")
@Table(name = "omnirio_account")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountEntity implements Serializable {

    @Id
    @Column(name="account_id",unique = true)
    private String accountId;

    @Column(name = "account_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "open_date")
    private Date openDate;

    @Column(name = "fk_customer_id")
    private String customerId;

    @NotNull
    @Column(name ="customer_name")
    private String customerName;

    @Column(name ="branch")
    private String branch;

    @Column(name ="minor_indicator")
    private String minorIndicator;


    public AccountEntity() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMinorIndicator() {
        return minorIndicator;
    }

    public void setMinorIndicator(String minorIndicator) {
        this.minorIndicator = minorIndicator;
    }
}
