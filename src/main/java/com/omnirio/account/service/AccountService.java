package com.omnirio.account.service;

import com.omnirio.account.entity.AccountEntity;
import com.omnirio.account.repository.AccountRepository;
import com.omnirio.account.util.Util;
import com.omnirio.account.vo.AccountDetail;
import com.omnirio.account.vo.AccountRequest;
import com.omnirio.account.vo.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    public String createAccount(AccountDetail accountDetail) {

        String userId = restTemplate.postForObject("http://customer-service/customers/", accountDetail.getCustomer(), String.class);

        AccountEntity accountEntity = mapToDetail(accountDetail, userId);
        accountEntity.setAccountId(Util.getUUID());
        AccountEntity response = accountRepository.saveAndFlush(accountEntity);

        return response.getAccountId();
    }


    public AccountRequest getAccount(String accountId) {
        AccountEntity accountEntity = accountRepository.getOne(accountId);
        return mapTo(accountEntity);
    }

    public List<AccountRequest> getAccounts() {
        List<AccountEntity> accountEntityList = accountRepository.findAll();
        return accountEntityList
                .stream()
                .map(this::mapTo)
                .collect(Collectors.toList());
    }

    public AccountRequest updateAccount(AccountRequest accountRequest, String accountId) {
        //first find the Customer/User details by given userId
        AccountEntity existingEntity = accountRepository.getOne(accountId);
        if (existingEntity == null) {
            //TODO throw error that entity doesn't exist
        }
        //update the entity with all fields sent in request by keeping the userId as it is.
        //as customer will remain same in updating customer, so keeping the customerId existing one
        AccountEntity updatedEntity = mapTo(accountRequest, existingEntity.getCustomerId());
        //As we are updating Account, so keeping the accountId existing one only.
        updatedEntity.setAccountId(existingEntity.getAccountId());
        AccountEntity result = accountRepository.save(updatedEntity);

        return mapTo(result);
    }

    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    }

    //Account Detail Methods, This will also hold information related to Customer associated with it
    public AccountDetail getAccountDetails(String accountId) {
        AccountEntity accountEntity = accountRepository.getOne(accountId);

        AccountDetail accountDetail = (AccountDetail) mapTo(accountEntity);
        accountDetail.setCustomer(getCustomer(accountEntity.getCustomerId()));
        return accountDetail;
    }

    public List<AccountDetail> getAccountDetailList() {

        List<AccountEntity> accountEntities = accountRepository.findAll();
        return accountEntities
                .parallelStream()
                .map(entity -> {
                    AccountDetail accountDetail = (AccountDetail) mapTo(entity);
                    accountDetail.setCustomer(getCustomer(entity.getCustomerId()));
                    return accountDetail;
                })
                .collect(Collectors.toList());
    }

    //private supporting methods
    private UserResponse getCustomer(String customerId) {
        UserResponse customer = restTemplate.getForObject("http://customer-service/customers/" + customerId, UserResponse.class);
        return customer;
    }


    private AccountEntity mapToDetail(AccountDetail accountDetail, String userId) {
        AccountEntity accountEntity = mapTo(accountDetail, userId);
        accountEntity.setMinorIndicator(minorIndicator(accountDetail.getCustomer().getDateOfBirth()));
        return accountEntity;
    }

    private AccountEntity mapTo(AccountRequest accountRequest, String userId) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountType(accountRequest.getAccountType());
        accountEntity.setOpenDate(accountRequest.getOpenDate());
        accountEntity.setCustomerId(userId);
        accountEntity.setCustomerName(accountRequest.getCustomerName());
        accountEntity.setBranch(accountRequest.getBranch());

        return accountEntity;
    }

    private AccountRequest mapTo(AccountEntity accountEntity) {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId(accountEntity.getAccountId());
        accountRequest.setAccountType(accountEntity.getAccountType());
        accountRequest.setOpenDate(accountEntity.getOpenDate());
        accountRequest.setCustomerName(accountEntity.getCustomerName());
        accountRequest.setBranch(accountEntity.getBranch());
        accountRequest.setMinorIndicator(accountEntity.getMinorIndicator());

        return accountRequest;
    }

    private String minorIndicator(Date dob) {
        long age = LocalDate
                .from(dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .until(LocalDate.now(), ChronoUnit.YEARS);
        if (age > 18) {
            return "N";
        } else {
            return "Y";
        }
    }
}
