package com.ebank.service;

import com.ebank.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    //private final JdbcTemplate jdbcTemplate;

    //@Autowired
    //public CustomerService(JdbcTemplate jdbcTemplate) {
      //  this.jdbcTemplate = jdbcTemplate;
    //}

    public Customer getCustomer(long id) {
        System.out.println("###CustomerService.getCustomer()");
        if(id == 1) {
            return new Customer(1, "Abe", "Abeson");
        }
        return null;
    }
}
