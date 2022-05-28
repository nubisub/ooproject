package com.example.data;

public class Account {
    private String nim;
    private String password;
    private String isDaftar;
    private String role;

    public Account(String nim, String password, String isDaftar, String role) {
        this.nim = nim;
        this.password = password;
        this.isDaftar = isDaftar;
        this.role = role;
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
