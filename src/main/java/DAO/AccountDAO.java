package DAO;

import java.sql.*;

import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {
    Account account;

    public AccountDAO() {
        this.account = new Account();
    }

    public AccountDAO(Account account) {
        this.account = account;
    }

    public Account login(Account account) {
        Connection dbConnection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT account_id FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) { // account exists in the database
                account.setAccount_id(rs.getInt (1));
                return account;
            }

        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }

        return null; // account doesn't exist in the database
    }

    public Account selectAccountByUsername(String username) {
        Connection dbConnection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT account_id, username, password FROM account WHERE username = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) { // account exists in the database
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }

        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }

        return null; // account doesn't exist in the database
    }

    public Account selectAccountById(int account_id) {
        Connection dbConnection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT account_id, username, password FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) { // account  exists in the database
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }

        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }

        return null; // account doesn't exist in the database
    }

    public Account insertAccount(Account account) {
        Connection dbConnection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";

            PreparedStatement prepStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepStatement.setString(1, account.username);
            prepStatement.setString(2, account.password);
            prepStatement.executeUpdate();
            ResultSet pkResultSet = prepStatement.getGeneratedKeys();
            if (pkResultSet.next()) {
                int generatedAccountId = pkResultSet.getInt(1);
                return new Account(generatedAccountId, account.getUsername(), account.getPassword());
            }

        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }

        return null; // couldn't insert new account;
    }
}