package com.ebank.web;

import com.ebank.domain.Customer;
import com.ebank.exception.ResourceNotFoundException;
import com.ebank.service.CustomerService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable(value = "id") long id) {
        System.out.println("###CustomerController.getCustomer()");

        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            throw new ResourceNotFoundException("No customer found with id=" + id);
        }

        return customer;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getCustomers() {
        System.out.println("###getCustomers()");
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(new Customer(1, "Abe", "Abeson"));
        customers.add(new Customer(2, "Bob", "Bobson"));
        return customers;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void createCustomer(@Valid @RequestBody Customer customer) {
        System.out.println("###addCustomer(): " + customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void updateCustomer(@PathVariable(value = "id") long id, @Valid @RequestBody Customer customer) {
        System.out.println("###updateCustomer(): " + customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable(value = "id") long id) {
        System.out.println("###deleteCustomer()");
    }
}
