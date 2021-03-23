package RequestMethods;

import com.server.rpc.Constants.Text;
import com.server.rpc.DAOAddress.AddressDAO;
import com.server.rpc.address.AddressService;
import com.server.rpc.data.JsonRpcKeys;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

public class Methods {

    public static Object getId(JSONObject obj){
        if (obj.isNull(JsonRpcKeys.ID_KEY))
            return null;
        return obj.get(JsonRpcKeys.ID_KEY);
    }
    public static String getText(){
        return Text.text.get(new Random().nextInt(Text.text.size()));
    }

    public static Integer generateInteger(){
        return new Random().nextInt(10);
    }

    public static String getVersion(){
        return "0.1";
    }

    public static List<String> get_peer_list(){
        AddressDAO addressDAO = new AddressService();
        return addressDAO.getAll();
    }

}
