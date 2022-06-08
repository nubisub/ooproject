package com.example.application.data.postgres;

import javax.persistence.PrePersist;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {
    public void Update(String nim, String nama, String alamat, String tanggal_lahir, String email, String phone) throws SQLException, URISyntaxException {
        String sql = "UPDATE oop.\"Mahasiswa\"\n" +
                "SET nama          =  ?,\n" +
                "    tanggal_lahir = ?,\n" +
                "    email         = ?,\n" +
                "    phone         = ?,\n" +
                "    alamat        = ?\n" +
                "WHERE nim LIKE '" + nim + "' ESCAPE '#';\n";
        Connect connect = Connect.getInstance();
        Connection connection = connect.getConnection();

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, nama);
        pstmt.setDate(2, java.sql.Date.valueOf(tanggal_lahir));
        pstmt.setString(3, email);
        pstmt.setString(4, phone);
        pstmt.setString(5, alamat);
        pstmt.executeUpdate();
//        pstmt.close();
//        connection.close();
    }
    public void updateStatus(String nim) throws SQLException, URISyntaxException {
        Connect connect = Connect.getInstance();
        Connection connection = connect.getConnection();

        String sql = "UPDATE oop.\"Mahasiswa\"\n" +
                "SET status = '1'\n" +
                "WHERE nim LIKE '" + nim + "' ESCAPE '#';\n";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.executeUpdate();

    }
    public void registrasiUKM(String nim, String UKM, int prioritas) throws SQLException, URISyntaxException {
        Connect connect = Connect.getInstance();
        Connection connection = connect.getConnection();

        String sql = "insert into oop.ukm_registration VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, nim);
        pstmt.setString(2, UKM);
        pstmt.setInt(3, prioritas);
        pstmt.setInt(4, 0);
        pstmt.executeUpdate();

    }


}
