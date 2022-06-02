package com.example.application.data.postgres;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {
    public Update(String nim, String nama, String alamat, String tanggal_lahir, String email, String phone) throws SQLException, URISyntaxException {
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
        pstmt.close();
        connection.close();
    }
}
