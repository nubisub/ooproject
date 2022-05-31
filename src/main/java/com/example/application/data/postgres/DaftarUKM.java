package com.example.application.data.postgres;

import com.mysql.cj.protocol.Resultset;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DaftarUKM {
    private static ArrayList<String> daftarUKM = new ArrayList<>();


//create constructor


    public DaftarUKM() {
        System.out.println("Daftar UKM");

        try{
            Connect connect = Connect.getInstance();
            Connection connection = connect.getConnection();

            String sql = "SELECT * FROM oop.ukm ORDER BY ukm";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
//                namaUKM = resultSet.getString("ukm").split(",");
                daftarUKM.add(resultSet.getString("ukm"));
//                daftarUKMCollection.add(resultSet.getString("ukm"));
////                daftarUKMCollection.spliterator();
//                daftarUKMList = Arrays.asList(namaUKM);
                System.out.println(resultSet.getString("ukm"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    public static ArrayList<String> getDaftarUKM() {
        return daftarUKM;
    }


    public static void main(String[] args) {
        DaftarUKM daftarUKM = new DaftarUKM();
//        String[] namaUKM = daftarUKM.getNamaUKM();
//        System.out.println(Arrays.toString(namaUKM));

    }
}
