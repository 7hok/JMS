package com.java.push.utils;

import java.util.HashMap;
import java.util.Map;

public class Response {
    
    public static Map<String, Object> getResponseBody(String msg, Object data,Boolean status ) 
    {
        Map<String, Object> response = new HashMap<>();
        response.put("msg", msg);
        response.put("data", data);
        response.put("status",status);
        return response;
    }

}