package com.example.application.data.postgres;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
        public static Connect instance;
        private  Connection connection;
        private static final String USERNAME = "postgres";
        private static final String PASSWORD = "postgres";
        private static final String CONN_STRING = "jdbc:postgresql://localhost:5432/postgres";
        private static final String DRIVER_CLASS = "org.postgresql.Driver";
        private static final String DB_NAME = "postgres";
        private static final String TABLE_NAME = "public.\"user\"";

        Connect() throws SQLException, URISyntaxException {
            try {
                Class.forName("org.postgresql.Driver");
                this.connection = DriverManager
                        .getConnection("jdbc:postgresql://ec2-44-194-117-205.compute-1.amazonaws.com:5432/dbo6310a8h28f",
                                "ynxmuwbcrpgyyq", "075a0a0320d8036fd0bf3b8facfe8b8fbf07b85e1935c461f14dedf0edc94e5a");
                System.out.println("Connected to PostgreSQL database!");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                System.exit(0);
            }
        }

        public static Connect getInstance() throws SQLException, URISyntaxException {
            if (instance == null) {
                instance = new Connect();
            }
            return instance;
        }

        public Connection getConnection() {
            return connection;
        }


    public static void main(String args[]) {
        try {
            Connect connect = Connect.getInstance();
            Connection connection = connect.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
