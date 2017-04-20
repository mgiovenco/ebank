package com.ebank.web;

import com.ebank.domain.Account;
import com.ebank.domain.Customer;
import com.ebank.exception.ResourceNotFoundException;
import com.ebank.exception.ValidationException;
import com.ebank.service.AccountService;
import com.ebank.service.CustomerService;
import com.ebank.util.ServerErrorGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final ServerErrorGenerator serverErrorGenerator;

    private static Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    public AccountController(AccountService accountService, CustomerService customerService, ServerErrorGenerator serverErrorGenerator) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.serverErrorGenerator = serverErrorGenerator;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Account getAccount(@PathVariable(value = "id") int id) {
        log.info("getAccount");

        serverErrorGenerator.generateRandomServerError();

        Account account = null;
        try {
            account = accountService.getAccount(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (account == null) {
            throw new ResourceNotFoundException("No account found with id=" + id);
        }

        return account;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> getAllAccounts() {
        log.info("getAllAccounts");

        serverErrorGenerator.generateRandomServerError();

        List<Account> accounts = new ArrayList<>();
        accounts = accountService.getAllAccounts();
        if (accounts == null) {
            throw new ResourceNotFoundException("No accounts found");
        }

        return accounts;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Account createAccount(@Valid @RequestBody Account account) throws Exception {
        log.info("createAccount");

        serverErrorGenerator.generateRandomServerError();

        int customerId = account.getCustomerId();
        Customer customer = customerService.getCustomer(customerId);

        if (customer == null) {
            throw new ResourceNotFoundException("No customer found with id=" + customerId);
        }

        return accountService.addAccount(account);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void updateAccount(@PathVariable(value = "id") int id, @Valid @RequestBody Account account) throws SQLException {
        log.info("updateAccount");

        serverErrorGenerator.generateRandomServerError();

        Account currentAccount = accountService.getAccount(id);
        if (currentAccount == null) {
            throw new ResourceNotFoundException("No account found with id=" + id);
        }

        Customer currentCustomer = customerService.getCustomer(account.getCustomerId());
        if (currentCustomer == null) {
            throw new ResourceNotFoundException("No customer found with id=" + account.getCustomerId());
        }

        if (currentAccount.getCustomerId() != account.getCustomerId()) {
            throw new ValidationException("Existing customer id (" + currentAccount.getCustomerId() +
                    ") cannot be changed to a new customer id ("
                    + account.getCustomerId() + ")");
        }

        accountService.updateAccount(account);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteAccount(@PathVariable(value = "id") int id) throws SQLException {
        log.info("deleteAccount");

        serverErrorGenerator.generateRandomServerError();

        Account currentAccount = accountService.getAccount(id);
        if (currentAccount == null) {
            throw new ResourceNotFoundException("No account found with id=" + id);
        }

        accountService.inactivateAccount(id);
    }

}
