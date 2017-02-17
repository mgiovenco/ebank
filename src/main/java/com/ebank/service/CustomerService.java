package com.ebank.service;

import com.ebank.dao.CustomerDao;
import com.ebank.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerDao customerDao;

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer getCustomer(int id) {
        return customerDao.selectCustomer(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer addCustomer(Customer customer) throws Exception {
        return customerDao.createCustomer(customer);
    }

    public void updateCustomer(int id, Customer customer) {
        customerDao.updateCustomer(id, customer);
    }

    public void inactivateCustomer(int id) {
        customerDao.inactivateCustomer(id);
    }

}
