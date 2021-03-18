package com.server.rpc.DAOAddress;

import java.util.List;

public interface AddressDAO {

    void addAddress(String address);
    boolean checkAddress(String address);
//    List<Address> getAll();
    List<String> getAll();
}
