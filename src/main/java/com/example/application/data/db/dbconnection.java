//package com.example.application.data.db;
//import java.io.Serializable;
//import java.sql.Array;
//import java.sql.Connection;
//
//public class dbconnection implements Serializable {
//    public static dbconnection instance;
//
//    private final String DB_TYPE = "jdbc:mysql";
//    private final String DB_HOST = "localhost";
//    private final String DB_PORT = "3306";
//    private final String DB_NAME = "mahasiswa";
//    private final String DB_USER = "root";
//    private final String DB_PASS = "";
//
//    public dbconnection() {
//    }
//
//    public static synchronized dbconnection getInstance() {
//        if (instance == null) {
//            instance = new dbconnection();
//        }
//        return instance;
//    }
//
//    private Connection getConnection() {
//        Connection connection = null;
//        try {
//            connection = java.sql.DriverManager.getConnection(DB_TYPE + "://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME, DB_USER, DB_PASS);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    private void closeConnection(Connection connection) {
//        try {
//            connection.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getMahasiswa() {
//        Connection connection = getConnection();
//        String result = "";
//        try {
//
//
//            java.sql.Statement statement = connection.createStatement();
//            java.sql.ResultSet resultSet = statement.executeQuery("SELECT * FROM mahasiswa");
//            while (resultSet.next()) {
////                array.add(resultSet.getString("nama"));
////                System.out.println(resultSet.getString("nama"));
//                result += resultSet.getString("nama") + " ";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            closeConnection(connection);
//        }
//        return result;
//    }
//}
//
//
//
//
