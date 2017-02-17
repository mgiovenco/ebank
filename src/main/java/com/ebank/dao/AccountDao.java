package com.ebank.dao;

import com.ebank.domain.Account;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountDao {

    private static final String SELECT_ACCOUNT = "SELECT id, type, balance, active, customer_id from account where id = ?";
    private static final String SELECT_ALL_ACCOUNTS = "SELECT id, type, balance, active, customer_id from account";
    private static final String INSERT_ACCOUNT = "INSERT into account (type, balance, active, customer_id) values (?, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT = "UPDATE account set type = ?, balance = ?, active = ?, customer_id = ? where id = ?";
    private static final String INACTIVATE_ACCOUNT ="UPDATE account set active = false where id = ?";

    public Account selectAccount(int id) {

        Account account = null;

        try {
            Connection conn = DBHelper.getconnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_ACCOUNT, id);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                account = new Account(resultSet.getInt("id"), resultSet.getString("type"), resultSet.getBigDecimal("balance"), resultSet.getBoolean("active"), resultSet.getInt("customer_id"));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }

        return account;
    }

    public List<Account> selectAllAccounts() {

        List<Account> accountList = new ArrayList<>();

        try {
            Connection conn = DBHelper.getconnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ACCOUNTS);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                accountList.add(new Account(resultSet.getInt("id"), resultSet.getString("type"), resultSet.getBigDecimal("balance"), resultSet.getBoolean("active"), resultSet.getInt("customer_id")));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }

        return accountList;
    }

    public Account createAccount(Account account) throws Exception {
        if (account != null) {
            try {
                Connection conn = DBHelper.getconnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getType());
                ps.setBigDecimal(2, account.getBalance());
                ps.setBoolean(3, account.getActive());
                ps.setInt(4, account.getCustomerId());

                int result = ps.executeUpdate();

                if (result == 0) {
                    throw new SQLException("Creating account failed, no rows affected.");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("GeneratedKey: " + generatedKeys.getInt(1));
                        return new Account(generatedKeys.getInt(1), account.getType(), account.getBalance(), account.getActive(), account.getCustomerId());
                    } else {
                        throw new SQLException("Creating account failed, no ID obtained.");
                    }
                }

            } catch (SQLException e) {
                System.out.println("SQLException: " + e);
            }
        } else {
            throw new Exception("Cannot insert null account object");
        }

        return null;
    }

    public void updateAccount(Account account) {
        try {
            Connection conn = DBHelper.getconnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_ACCOUNT);

            ps.setString(1, account.getType());
            ps.setBigDecimal(2, account.getBalance());
            ps.setBoolean(3, account.getActive());
            ps.setInt(4, account.getCustomerId());
            ps.setInt(5, account.getId());

            int result = ps.executeUpdate();

            if (result == 0) {
                System.out.println("No account found for account id: " + account.getId());
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }
    }

    public void inactivateAccount(int id) {
        try {
            Connection conn = DBHelper.getconnection();
            PreparedStatement ps = conn.prepareStatement(INACTIVATE_ACCOUNT);

            ps.setInt(1, id);

            int result = ps.executeUpdate();

            if (result == 0) {
                System.out.println("No account found for account id: " + id);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }
    }
}
