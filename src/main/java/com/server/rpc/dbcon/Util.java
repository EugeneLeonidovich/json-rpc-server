package com.server.rpc.dbcon;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/decentralized_messenger?serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection to DB");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

}
