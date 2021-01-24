package com.omnirio.account.controller;

import com.omnirio.account.service.AccountService;
import com.omnirio.account.vo.AccountDetail;
import com.omnirio.account.vo.AccountRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/")
    @ApiOperation(value = "Create Account",tags = {"Account"})
    public ResponseEntity<String> create(@RequestBody AccountDetail accountDetail){
        String response = accountService.createAccount(accountDetail);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/{accountId}")
    @ApiOperation(value = "Retrieve Account Info",tags = {"Account"})
    public ResponseEntity<AccountRequest> getAccount(@PathVariable(value = "accountId") String accountId){
        AccountRequest response = accountService.getAccount(accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    @ApiOperation(value = "Retrieve All Account Info",tags = {"Account"})
    public ResponseEntity<List<AccountRequest>> getAccountList(){
        List<AccountRequest> response = accountService.getAccounts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{accountId}")
    @ApiOperation(value = "Update Account Info",tags = {"Account"})
    public ResponseEntity<AccountRequest> updateAccount(@RequestBody AccountRequest accountRequest,@PathVariable(value = "accountId") String accountId){
        AccountRequest response = accountService.updateAccount(accountRequest,accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    @ApiOperation(value = "Delete Account Info",tags = {"Account"})
    public ResponseEntity<Void> deleteAccount(@PathVariable(value = "accountId") String accountId){
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //account detail related APIs
    @GetMapping("/{accountId}/details")
    @ApiOperation(value = "Retrieve Account Details",tags = {"Account Details"})
    public ResponseEntity<AccountDetail> getAccountDetails(@PathVariable(value = "accountId") String accountId){
        AccountDetail response = accountService.getAccountDetails(accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/details")
    @ApiOperation(value = "Retrieve All Account Details",tags = {"Account Details"})
    public ResponseEntity<List<AccountDetail>> getAccountDetailList(){
        List<AccountDetail> response = accountService.getAccountDetailList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
