package com.ebank.service;

import com.ebank.dao.AccountDao;
import com.ebank.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AccountService {

    private final AccountDao accountDao;

    @Autowired
    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account getAccount(int id) throws SQLException{
        return accountDao.selectAccount(id);
    }

    public List<Account> getAllAccounts() {
        return accountDao.selectAllAccounts();
    }

    public Account addAccount(Account account) throws Exception {
        return accountDao.createAccount(account);
    }

    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    public void inactivateAccount(int id) {
        accountDao.inactivateAccount(id);
    }
}
