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
import java.sql.SQLException;
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
    public Customer getCustomer(@PathVariable(value = "id") int id) {
        Customer customer = customerService.getCustomer(id);

        if (customer == null) {
            throw new ResourceNotFoundException("No customer found with id=" + id);
        }

        return customer;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        if (customers == null) {
            throw new ResourceNotFoundException("No customers found");
        }

        return customers;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Customer createCustomer(@Valid @RequestBody Customer customer) throws Exception {
        return customerService.addCustomer(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void updateCustomer(@PathVariable(value = "id") int id, @Valid @RequestBody Customer customer) {
        Customer currentCustomer = customerService.getCustomer(id);

        if (currentCustomer == null) {
            throw new ResourceNotFoundException("No customer found with id=" + id);
        }

        customerService.updateCustomer(id, customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable(value = "id") int id) throws SQLException {
        Customer customer = customerService.getCustomer(id);

        if (customer == null) {
            throw new ResourceNotFoundException("No customer found with id=" + id);
        }

        customerService.inactivateCustomer(id);
    }
}
