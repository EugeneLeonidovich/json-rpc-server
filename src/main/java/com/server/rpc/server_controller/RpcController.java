package com.server.rpc.server_controller;

import com.server.rpc.DAOAddress.AddressDAO;
import com.server.rpc.address.AddressService;
import com.server.rpc.data.JsonRpcError;
import com.server.rpc.data.JsonRpcKeys;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.server.rpc.Constants.Text;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/")
public class RpcController {

    @PostMapping(value = "/method", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getMethod(
            @RequestBody String obj, HttpServletRequest request
    ){
//        long time = System.currentTimeMillis();
//        System.out.println(time);
        System.out.println((request.getHeader("User-Agent") + " -> " + request.getRemoteAddr()));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                addIp(request.getRemoteAddr());
            }
        });
        thread.start();

        JSONObject json = new JSONObject(obj);
        Map<String, Object> resp = new HashMap<>();
        resp.put(JsonRpcKeys.JSON_RPC_KEY, JsonRpcKeys.JSON_RPC_VERSION);
        resp.put(JsonRpcKeys.ID_KEY, getId(json));
        if (JsonRpcError.requestIsValid(json)) {
            switch (json.getString("method")){
                case "generateInteger":
                    resp.put(JsonRpcKeys.RESULT_KEY, generateInteger());
                    break;
                case "getText":
                    resp.put(JsonRpcKeys.RESULT_KEY, getText());
                    break;
                case "getVersion":
                    resp.put(JsonRpcKeys.RESULT_KEY, getVersion());
                    break;
                case "get_peer_list":
                    resp.put(JsonRpcKeys.RESULT_KEY, get_peer_list());
                    break;
                default:
                    resp.put(JsonRpcKeys.RESULT_KEY, -1);
                    break;
            }
            //System.out.println((System.currentTimeMillis()-time));
            return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);
        }
        resp.put(JsonRpcKeys.ERROR_KEY, JsonRpcError.produceError(JsonRpcError.INVALID_REQUES));

        //System.out.println((System.currentTimeMillis()-time)/1000);
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }


    private Object getId(JSONObject obj){
        if (obj.isNull(JsonRpcKeys.ID_KEY))
            return null;
        return obj.get(JsonRpcKeys.ID_KEY);
    }
    private String getText(){
        return Text.text.get(new Random().nextInt(Text.text.size()));
    }

    private Integer generateInteger(){
        return new Random().nextInt(10);
    }

    private String getVersion(){
        return "0.1";
    }

    private List<String> get_peer_list(){
        AddressDAO addressDAO = new AddressService();
        List<String> ip = addressDAO.getAll();
        return ip;
    }

    private void addIp(String ip){
        AddressDAO addressDAO = new AddressService();
        addressDAO.addAddress(ip);
    }
}