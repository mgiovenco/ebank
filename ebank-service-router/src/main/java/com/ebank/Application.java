package com.ebank;

import com.ebank.domain.Account;
import com.ebank.domain.Customer;
import com.ebank.exception.ResourceNotFoundException;
import com.google.common.base.Stopwatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
@RibbonClient(name = "ebank-service", configuration = EbankServiceConfiguration.class)
public class Application {

    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    // ### Customer endpoints ####

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        return this.restTemplate.getForObject("http://ebank-service/customers/" + id, Customer.class);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllCustomers() throws ResourceNotFoundException {
        Customer[] response = this.restTemplate.getForObject("http://ebank-service/customers/", Customer[].class);
        return Arrays.asList(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public Customer createCustomer(@Valid @RequestBody Customer customer) throws Exception {
        return this.restTemplate.postForObject("http://ebank-service/customers", customer, Customer.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public void updateCustomer(@PathVariable(value = "id") int id, @Valid @RequestBody Customer customer) throws ResourceNotFoundException {
        this.restTemplate.put("http://ebank-service/customers/" + id, customer, Customer.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        this.restTemplate.delete("http://ebank-service/customers/" + id);
    }

    // ### Account endpoints ####

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Account getAccount(@PathVariable(value = "id") int id) {
        return this.restTemplate.getForObject("http://ebank-service/accounts/" + id, Account.class);
    }

    @RequestMapping(value = "/accounts/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> getAllAccounts() {
        Account[] response = this.restTemplate.getForObject("http://ebank-service/accounts/", Account[].class);
        return Arrays.asList(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/accounts/", method = RequestMethod.POST)
    public Account createAccount(@Valid @RequestBody Account account) throws Exception {
        return this.restTemplate.postForObject("http://ebank-service/accounts", account, Account.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.PUT)
    public void updateAccount(@PathVariable(value = "id") int id, @Valid @RequestBody Account account) throws SQLException {
        this.restTemplate.put("http://ebank-service/accounts/" + id, account, Account.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
    public void deleteAccount(@PathVariable(value = "id") int id) throws SQLException {
        this.restTemplate.delete("http://ebank-service/accounts/" + id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void distributedTest() throws Exception {
        System.out.println("Distributed load test - START");
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i <= 1000; i++) {
            System.out.print("Test Customer - attempt=" + i);
            try{
                Customer customerResult = restTemplate.getForObject("http://ebank-service/customers/1", Customer.class);
                System.out.println(", customerResult=" + customerResult);
            }
            catch (Exception e){
                System.out.println("Exception encountered: " + e);
            }

        }
        stopwatch.stop();
        System.out.println("Distributed load test - FINISH");
        System.out.println("Total time: " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}