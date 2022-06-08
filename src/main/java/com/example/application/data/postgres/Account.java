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
    private String status;
    private String role;
    private String email;
    private String phone;
    private String alamat;
    private LocalDate tanggalLahir;

    private String ukm1;
    private int ukm1Status;
    private String ukm2;
    private int ukm2Status;

    private String ukm3;
    private int ukm3Status;
    private String ukm4;
    private int ukm4Status;
    private String ukm5;
    private int ukm5Status;

    private String adminUKM;


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
                this.status = resultSet2.getString("status");
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
        this.ukm1 = "";
        this.ukm2 = "";
        this.ukm3 = "";
        this.ukm4 = "";
        this.ukm5 = "";
        this.ukm1Status = 0;
        this.ukm2Status = 0;
        this.ukm3Status = 0;
        this.ukm4Status = 0;
        this.ukm5Status = 0;

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
                this.status = resultSet2.getString("status");
                this.phone = resultSet2.getString("phone");
                this.alamat = resultSet2.getString("alamat");
                this.tanggalLahir = resultSet2.getDate("tanggal_lahir").toLocalDate();
                this.email = resultSet2.getString("email");
            }
            String query3 = "SELECT * FROM oop.ukm_registration WHERE nim = '" + nim + "' ORDER BY prioritas ";

            ResultSet resultSet3 = connection.createStatement().executeQuery(query3);
            while(resultSet3.next()){
                if(resultSet3.getInt("prioritas") == 1){
                    this.ukm1 = resultSet3.getString("ukm");
                    this.ukm1Status = resultSet3.getInt("statusDaftar");
                }
                if(resultSet3.getInt("prioritas") == 2){
                    this.ukm2 = resultSet3.getString("ukm");
                    this.ukm2Status = resultSet3.getInt("statusDaftar");
                }
                if(resultSet3.getInt("prioritas") == 3){
                    this.ukm3 = resultSet3.getString("ukm");
                    this.ukm3Status = resultSet3.getInt("statusDaftar");
                }
                if(resultSet3.getInt("prioritas") == 4){
                    this.ukm4 = resultSet3.getString("ukm");
                    this.ukm4Status = resultSet3.getInt("statusDaftar");
                }
                if(resultSet3.getInt("prioritas") == 5){
                    this.ukm5 = resultSet3.getString("ukm");
                    this.ukm5Status = resultSet3.getInt("statusDaftar");
                }
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUkm1() {
        return ukm1;
    }

    public void setUkm1(String ukm1) {
        this.ukm1 = ukm1;
    }

    public String getUkm2() {
        return ukm2;
    }

    public void setUkm2(String ukm2) {
        this.ukm2 = ukm2;
    }

    public String getUkm3() {
        return ukm3;
    }

    public void setUkm3(String ukm3) {
        this.ukm3 = ukm3;
    }

    public String getUkm4() {
        return ukm4;
    }

    public void setUkm4(String ukm4) {
        this.ukm4 = ukm4;
    }

    public String getUkm5() {
        return ukm5;
    }

    public void setUkm5(String ukm5) {
        this.ukm5 = ukm5;
    }

    public int getUkm1Status() {
        return ukm1Status;
    }

    public void setUkm1Status(int ukm1Status) {
        this.ukm1Status = ukm1Status;
    }

    public int getUkm2Status() {
        return ukm2Status;
    }

    public void setUkm2Status(int ukm2Status) {
        this.ukm2Status = ukm2Status;
    }

    public int getUkm3Status() {
        return ukm3Status;
    }

    public void setUkm3Status(int ukm3Status) {
        this.ukm3Status = ukm3Status;
    }

    public int getUkm4Status() {
        return ukm4Status;
    }

    public void setUkm4Status(int ukm4Status) {
        this.ukm4Status = ukm4Status;
    }

    public int getUkm5Status() {
        return ukm5Status;
    }

    public void setUkm5Status(int ukm5Status) {
        this.ukm5Status = ukm5Status;
    }

    public String getAdminUKM() {
        return adminUKM;
    }

    public void setAdminUKM(String adminUKM) {
        this.adminUKM = adminUKM;
    }

    public void refresh(){
        setAlamat("");
        setEmail("");
        setStatus("");
        setNama("");
        setPassword("");
        setPhone("");
        setRole("");
//        setTanggalLahir("");
        setUkm1("");
        setUkm2("");
        setUkm3("");
        setUkm4("");
        setUkm5("");

    }
}
