package Service;

import DAO.AccountDAO;
import Model.Account;


public class AccountService {
    AccountDAO accountDAO;

    public AccountService () {
        this.accountDAO = new AccountDAO();
    }
    public AccountService (AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount (Account account) {
        String username = account.getUsername ();
        if ((username != null) && (username.length() >= 1) &&  (account.getPassword ().length() >= 4)) {
            if (accountDAO.selectAccountByUsername(username) == null) {
                return accountDAO.insertAccount(account);
            }
        }

        return null;
    }

    public Account login (Account account) {
        return accountDAO.login (account);
    }
}