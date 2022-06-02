package com.example.application.data.postgres;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
        public static Connect instance;
        private  Connection connection;

        Connect() {
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

}
