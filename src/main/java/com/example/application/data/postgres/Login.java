package com.example.application.data.postgres;

import com.example.data.Account;

import java.sql.Connection;
import java.util.ArrayList;

public class Login {
    Account account;
    ArrayList<Account> accounts;

    public Login() {
        try {
            Connect connect = Connect.getInstance();
            Connection connection = connect.getConnection();

            String sql = "SELECT * FROM oop.login";
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String nim = resultSet.getString("nim");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String isDaftar = resultSet.getString("isDaftar");
                Account account = new Account(nim, password, role, isDaftar);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Login login = new Login();

    }

}
