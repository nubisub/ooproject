package com.example.application.data.postgres;

import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;


public class Account {
    public  static Account instance;
    private String nama;
    private String nim;
    private String password;
    private String isDaftar;
    private String role;
    private String email;
    private String phone;
    private String alamat;
    private LocalDate tanggalLahir;

    public Account(String nim) {
        this.nim = nim;
        try{
            Connect connect = Connect.getInstance();
            Connection connection = connect.getConnection();
            String query = "SELECT * FROM oop.login WHERE nim = '" + nim + "'";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                this.password = resultSet.getString("password");
                this.role = resultSet.getString("role");
            }
            String query2 = "SELECT * FROM oop.\"Mahasiswa\" WHERE nim = '" + nim + "'";
            ResultSet resultSet2 = connection.createStatement().executeQuery(query2);
            while (resultSet2.next()) {
                this.nama = resultSet2.getString("nama");
                this.isDaftar = resultSet2.getString("status");
                this.phone = resultSet2.getString("phone");
                this.alamat = resultSet2.getString("alamat");
                this.tanggalLahir = resultSet2.getDate("tanggal_lahir").toLocalDate();
                this.email = resultSet2.getString("email");
            }


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
        try{
            Connect connect = Connect.getInstance();
            Connection connection = connect.getConnection();
            String query = "SELECT * FROM oop.login WHERE nim = '" + nim + "'";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                this.password = resultSet.getString("password");
                this.role = resultSet.getString("role");
            }
            String query2 = "SELECT * FROM oop.\"Mahasiswa\" WHERE nim = '" + nim + "'";
            ResultSet resultSet2 = connection.createStatement().executeQuery(query2);
            while (resultSet2.next()) {
                this.nama = resultSet2.getString("nama");
                this.isDaftar = resultSet2.getString("status");
                this.phone = resultSet2.getString("phone");
                this.alamat = resultSet2.getString("alamat");
                this.tanggalLahir = resultSet2.getDate("tanggal_lahir").toLocalDate();
                this.email = resultSet2.getString("email");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = LocalDate.parse(tanggalLahir);
    }
}
