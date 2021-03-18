package com.server.rpc.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public enum JsonRpcError {
    PARCE_ERROR,
    INVALID_REQUES,
    METHOD_NOT_FOUND,
    INVALID_PARAMS,
    INTERNAL_ERROR;

    public int code(){
        switch (this){
            case PARCE_ERROR:
                return -32700;
            case INVALID_REQUES:
                return -32600;
            case METHOD_NOT_FOUND:
                return -32602;
            case INVALID_PARAMS:
                return -32603;
            case INTERNAL_ERROR:
                return -32604;
            default:
                return -1;
        }
    }

    public String message(){
        switch (this){
            case PARCE_ERROR:
                return "JSON parce error";
            case INVALID_REQUES:
                return "Invalid reques";
            case METHOD_NOT_FOUND:
                return "Method not found";
            case INVALID_PARAMS:
                return "Invalid parameters";
            case INTERNAL_ERROR:
                return "Internal error";
            default:
                return "Unidentified error";
        }
    }
    public static Map<String, Object> produceError(JsonRpcError rpcError){
        Map<String, Object> error = new HashMap<>();
        error.put(JsonRpcKeys.CODE_KEY, rpcError.code());
        error.put(JsonRpcKeys.METHOD_KEY, rpcError.message());
        error.put(JsonRpcKeys.DATA_KEY, null);
        return error;
    }
    public static boolean requestIsValid(JSONObject obj){
        return obj.has(JsonRpcKeys.ID_KEY)
                && obj.has(JsonRpcKeys.METHOD_KEY)
                && obj.has(JsonRpcKeys.PARAMS_KEY)
                && obj.has(JsonRpcKeys.JSON_RPC_KEY);
    }
}
