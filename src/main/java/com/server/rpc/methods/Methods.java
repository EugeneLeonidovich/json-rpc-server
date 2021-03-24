package com.server.rpc.methods;

import com.server.rpc.Constants.Text;
import com.server.rpc.DAOAddress.AddressDAO;
import com.server.rpc.address.AddressService;
import com.server.rpc.data.JsonRpcKeys;
import org.json.JSONObject;

import java.util.Random;

public class Methods {

    public static Object getId(JSONObject json) {
        return json.isNull(JsonRpcKeys.ID_KEY) ? null :
                json.get(JsonRpcKeys.ID_KEY);
    }

    public static Object generateInteger() {
        return new Random().nextInt(5);
    }

    public static Object getText() {
        return Text.text.get(new Random().nextInt(Text.text.size()));
    }

    public static Object getVersion() {
        return "0.1";
    }

    public static Object get_peer_list() {
        AddressDAO dao = new AddressService();
        return dao.getAll();
    }
}
