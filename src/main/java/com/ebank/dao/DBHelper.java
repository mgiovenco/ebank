package com.ebank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Helper class for connecting to AWS MySQL DB.
 */
public class DBHelper {

    public static Connection getconnection() {

        String connectionUrl = "jdbc:mysql://ebank.c5qycvuwlvdp.us-east-1.rds.amazonaws.com:3306/ebank";
        String dbUser = "mgiovenco";
        String dbPwd = "mgiovenco";
        Connection conn = null;

        try {
            return conn = (Connection) DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("fetch error" + e.getLocalizedMessage());
        }

        return conn;
    }
}