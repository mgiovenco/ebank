package com.ebank.dao;

import com.ebank.domain.Customer;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerDao {

    private static final String SELECT_CUSTOMER = "SELECT id, first_name, last_name, phone, active from customer where id = ?";
    private static final String SELECT_ALL_CUSTOMERS = "SELECT id, first_name, last_name, phone, active from customer";
    private static final String INSERT_CUSTOMER = "INSERT into customer (first_name, last_name, phone, active) values (?, ?, ? ,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE customer set first_name = ?, last_name = ?, phone = ?, active = ? where id = ?";
    private static final String INACTIVATE_ACCOUNT = "UPDATE customer set active = false where id = ?";

    public Customer selectCustomer(int id) {
        Customer customer = null;
        Connection conn = null;
        try {
            conn = DBHelper.getconnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_CUSTOMER, id);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                customer = new Customer(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("phone"), resultSet.getBoolean("active"));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        } finally {
            // Always be closing
            try {
                conn.close();
            } catch (Exception e) { }
        }
        return customer;
    }


    public List<Customer> selectAllCustomers() {

        List<Customer> customerList = new ArrayList<>();
        Connection conn = null;
        try {
             conn = DBHelper.getconnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_ALL_CUSTOMERS);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                customerList.add(new Customer(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("phone"), resultSet.getBoolean("active")));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        } finally {
            // Always be closing
            try {
                conn.close();
            } catch (Exception e) { /* ignored */ }
        }

        return customerList;
    }

    public Customer createCustomer(Customer customer) throws Exception {
        Connection conn = null;
        if (customer != null) {
            try {
                conn = DBHelper.getconnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_CUSTOMER, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.setString(3, customer.getPhone());
                ps.setBoolean(4, customer.getActive());

                int result = ps.executeUpdate();

                if (result == 0) {
                    throw new SQLException("Creating customer failed, no rows affected.");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("GeneratedKey: " + generatedKeys.getInt(1));
                        return new Customer(generatedKeys.getInt(1), customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getActive());
                    } else {
                        throw new SQLException("Creating customer failed, no ID obtained.");
                    }
                }

            } catch (SQLException e) {
                System.out.println("SQLException: " + e);
            } finally {
                // Always be closing
                try {
                    conn.close();
                } catch (Exception e) { /* ignored */ }
            }
        } else {
            throw new Exception("Cannot insert null customer object");
        }
        return null;
    }

    public void updateCustomer(int id, Customer customer) {
        Connection conn = null;

        try {
            conn = DBHelper.getconnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_CUSTOMER);

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPhone());
            ps.setBoolean(4, customer.getActive());
            ps.setInt(5, id);

            int result = ps.executeUpdate();

            if (result == 0) {
                System.out.println("No customer found for customer id: " + customer.getId());
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        } finally {
            // Always be closing
            try {
                conn.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    public void inactivateCustomer(int id) {
        Connection conn = null;
        try {
            conn = DBHelper.getconnection();
            PreparedStatement ps = conn.prepareStatement(INACTIVATE_ACCOUNT);

            ps.setInt(1, id);

            int result = ps.executeUpdate();

            if (result == 0) {
                System.out.println("No customer found for customer id: " + id);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        } finally {
            // Always be closing
            try {
                conn.close();
            } catch (Exception e) { /* ignored */ }
        }
    }
}