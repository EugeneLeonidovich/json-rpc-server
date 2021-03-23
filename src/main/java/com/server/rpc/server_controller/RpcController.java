package com.server.rpc.server_controller;

import RequestMethods.Methods;
import com.server.rpc.DAOAddress.AddressDAO;
import com.server.rpc.address.AddressService;
import com.server.rpc.data.JsonRpcError;
import com.server.rpc.data.JsonRpcKeys;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
        resp.put(JsonRpcKeys.ID_KEY, Methods.getId(json));
        if (JsonRpcError.requestIsValid(json)) {
            switch (json.getString("method")){
                case "generateInteger":
                    resp.put(JsonRpcKeys.RESULT_KEY, Methods.generateInteger());
                    break;
                case "getText":
                    resp.put(JsonRpcKeys.RESULT_KEY, Methods.getText());
                    break;
                case "getVersion":
                    resp.put(JsonRpcKeys.RESULT_KEY, Methods.getVersion());
                    break;
                case "get_peer_list":
                    resp.put(JsonRpcKeys.RESULT_KEY, Methods.get_peer_list());
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


    private void addIp(String ip){
        AddressDAO addressDAO = new AddressService();
        addressDAO.addAddress(ip);
    }
}