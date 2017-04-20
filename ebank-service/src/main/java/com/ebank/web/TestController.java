package com.ebank.web;

import com.ebank.domain.Account;
import com.ebank.domain.Customer;
import com.google.common.base.Stopwatch;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {

    private final RestTemplate restTemplate;

    public TestController() {
        this.restTemplate = new RestTemplate();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void test() {
        System.out.println("###Testing Start###");
        System.out.println();

        System.out.println("1. Test Customer Creation");
        Customer customer1 = new Customer(0, "Bob", "Bobson", "888-777-6666", true);
        Customer customerResult = restTemplate.postForObject("http://localhost:8080/customers", customer1, Customer.class);
        System.out.println("result: " + customerResult);
        System.out.println();

        System.out.println("2. Test Customer Select");
        customerResult = restTemplate.getForObject("http://localhost:8080/customers/" + customerResult.getId(), Customer.class);
        System.out.println("result: " + customerResult);
        System.out.println();

        System.out.println("3. Test Customer Select All");
        List<Customer> customerList = new ArrayList<>();
        customerList = restTemplate.getForObject("http://localhost:8080/customers/", ArrayList.class);
        System.out.println("result: " + customerList);
        System.out.println();

        System.out.println("4. Test Customer Update");
        Customer customer2 = new Customer(0, "Cal", "Calson", "777-666-5555", true);
        restTemplate.put("http://localhost:8080/customers/" + customerResult.getId(), customer2, Customer.class);
        customerResult = restTemplate.getForObject("http://localhost:8080/customers/" + customerResult.getId(), Customer.class);
        System.out.println("result (after update): " + customerResult);
        System.out.println();

        System.out.println("5. Test Customer Delete (soft)");
        restTemplate.delete("http://localhost:8080/customers/" + customerResult.getId(), Customer.class);
        customerResult = restTemplate.getForObject("http://localhost:8080/customers/" + customerResult.getId(), Customer.class);
        System.out.println("result (after delete): " + customerResult);
        System.out.println();

        System.out.println("6. Test Account Creation");
        Account account1 = new Account(0, "CHECKING", new BigDecimal("500.00"), true, customerResult.getId());
        Account accountResult = restTemplate.postForObject("http://localhost:8080/accounts", account1, Account.class);
        System.out.println("result: " + accountResult);
        System.out.println();

        System.out.println("7. Test Account Select");
        accountResult = restTemplate.getForObject("http://localhost:8080/accounts/" + accountResult.getId(), Account.class);
        System.out.println("result: " + accountResult);
        System.out.println();

        System.out.println("8. Test Account Select All");
        List<Account> accountList = new ArrayList<>();
        accountList = restTemplate.getForObject("http://localhost:8080/accounts/", ArrayList.class);
        System.out.println("result: " + accountList);
        System.out.println();

        System.out.println("9. Test Account Update");
        Account account2 = new Account(accountResult.getId(), "SAVINGS", new BigDecimal("1000.00"), true, customerResult.getId());
        restTemplate.put("http://localhost:8080/accounts/" + accountResult.getId(), account2, Account.class);
        accountResult = restTemplate.getForObject("http://localhost:8080/accounts/" + accountResult.getId(), Account.class);
        System.out.println("result (after update): " + accountResult);
        System.out.println();

        System.out.println("10. Test Account Delete (soft)");
        restTemplate.delete("http://localhost:8080/accounts/" + accountResult.getId(), Account.class);
        accountResult = restTemplate.getForObject("http://localhost:8080/accounts/" + accountResult.getId(), Account.class);
        System.out.println("result (after delete): " + accountResult);
        System.out.println();

        System.out.println("###Testing Complete###");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/loadtest", method = RequestMethod.POST)
    public void loadTest() throws InterruptedException {
        System.out.println("###Load Testing Start###");
        System.out.println();

        Stopwatch stopwatch = Stopwatch.createStarted();

        int successes = 0;
        boolean errorEncountered = false;
        int delayInMillis = 1000;

        for (int i = 0; i <= 1000; i++) {
            System.out.println("Test Customer Creation (" + i + ")");
            Customer customer1 = new Customer(0, "Bob", "Bobson", "888-777-6666", true);

            try{
                Customer customerResult = restTemplate.postForObject("http://localhost:8080/customers", customer1, Customer.class);
                System.out.println("Result: " + customerResult);
                successes++;
                if(errorEncountered && successes >= 5) {
                    delayInMillis = 1000;
                    errorEncountered = false;
                    System.out.println("Resetting delay due to successful attempts, new delayInMillis = " + delayInMillis);
                }
            } catch (Exception ex) {
                System.out.println("Server exception encountered, sleeping...");
                Thread.sleep(delayInMillis);
                delayInMillis = delayInMillis * 2;
                errorEncountered = true;
                successes = 0;
                System.out.println("Sleep over, reattempting.  New delayInMillis = " + delayInMillis);
            }

            System.out.println();
        }

        stopwatch.stop();

        System.out.println("Total time: " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds");

        System.out.println("###Load Testing Complete###");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/loadtest2", method = RequestMethod.POST)
    public void loadTes2t() throws InterruptedException {

        System.out.println("Loadtest2 started");

        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i <= 1000; i++) {
            System.out.print("Test Customer - attempt=" + i);
            try{
                Customer customerResult = restTemplate.getForObject("http://localhost:8080/customers/" + 1, Customer.class);
                System.out.println(", customerResult=" + customerResult);
            }
            catch (Exception e){
                System.out.println("Exception encountered: " + e);
            }

        }

        stopwatch.stop();

        System.out.println("Total time: " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds");
    }
}
