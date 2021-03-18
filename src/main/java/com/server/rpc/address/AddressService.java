package com.server.rpc.address;

import com.server.rpc.DAOAddress.AddressDAO;
import com.server.rpc.dbcon.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressService extends Util implements AddressDAO {

    private Connection connection = getConnection();

    @Override
    public void addAddress(String address) {
        PreparedStatement statement = null;
        if (!checkAddress(address)){
            //language=sql
            String sql = "INSERT INTO ipaddresses(ip) VALUES(?)";
            try {
                statement = connection.prepareStatement(sql);
                statement.setString(1, address);
                statement.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try{
                    if(statement != null)
                        statement.close();
                    if(connection != null)
                        connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean checkAddress(String address) {
        Statement statement = null;
        String sql = "SELECT ip FROM ipaddresses " +
                "WHERE ip='"+address+"';";
        try {
            statement = connection.createStatement();
            if(statement.executeQuery(sql).first()) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getAll() {
        Statement statement = null;
        List<String> addresses = new ArrayList<>();
        //language=sql
        String sql = "SELECT * FROM ipaddresses";
        try{
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next())
                addresses.add(set.getString("ip"));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(statement != null)
                    statement.close();
                if(connection != null)
                    connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return addresses;
    }
}
