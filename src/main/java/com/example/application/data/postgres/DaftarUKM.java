package com.example.application.data.postgres;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DaftarUKM {
    private static ArrayList<String> daftarUKM = new ArrayList<>();

    public DaftarUKM() {
//        System.out.println("Daftar UKM");

        try{
            Connect connect = Connect.getInstance();
            Connection connection = connect.getConnection();

            String sql = "SELECT * FROM oop.ukm ORDER BY ukm";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                daftarUKM.add(resultSet.getString("ukm"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    public static ArrayList<String> getDaftarUKM() {
        return daftarUKM;
    }



}
