package com.example.application.data.postgres;

import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Connection;
import java.sql.ResultSet;

public class Account {
    public  static Account instance;
    private String nim;
    private String password;
    private String isDaftar;
    private String role;

    public Account(String nim) {
        this.nim = nim;
        try{
            Connect connect = Connect.getInstance();
            Connection connection = connect.getConnection();
            String query = "SELECT * FROM oop.login WHERE nim = '" + nim + "'";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                this.password = resultSet.getString("password");
                this.isDaftar = resultSet.getString("isDaftar");
                this.role = resultSet.getString("role");
            }
            System.out.println(nim);
            System.out.println(password);
            System.out.println(isDaftar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  static Account getInstance() {
        String nim = SecurityContextHolder.getContext().getAuthentication().getName();
        if (instance == null) {
            instance = new Account(nim);
        }
        return instance;
    }
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsDaftar() {
        return isDaftar;
    }

    public void setIsDaftar(String isDaftar) {
        this.isDaftar = isDaftar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
