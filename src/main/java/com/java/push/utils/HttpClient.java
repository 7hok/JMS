package com.java.push.utils;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


public class HttpClient {

    private String server="";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;
    private Logger logger = LoggerFactory.getLogger(HttpClient.class);
    
    public HttpClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
      }
    
    public void setHeader(String key,String value){
        this.headers.add(key,value);
    }

    public JSONObject get(final String uri) {

        logger.info("[ Request to : "+ uri+"]");

        final HttpEntity<String> requestEntity = new HttpEntity<String>("", this.headers);
        final ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity,
        String.class);

        logger.info("[ Response From : "+ uri+"]");
        logger.info(responseEntity.getBody());
        this.setStatus(responseEntity.getStatusCode());
        // logger.info(responseEntity.getBody().toStrng());
        return new JSONObject(responseEntity.getBody());
    }


    public JSONObject post(final String uri, final String json) throws Exception {
        System.out.println(server);
        final HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        final ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return new JSONObject(responseEntity.getBody());
    }

    // public void put(final String uri, final String json) {
    //     final HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
    //     final ResponseEntity<JSONObject> responseEntity = rest.exchange(server + uri, HttpMethod.PUT, requestEntity,
    //         JSONObject.class);
    //     this.setStatus(responseEntity.getStatusCode());
    // }

    // public void delete(final String uri) {
    //     final HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
    //     final ResponseEntity<JSONObject> responseEntity = rest.exchange(server + uri, HttpMethod.DELETE, requestEntity,
    //         JSONObject.class);
    //     this.setStatus(responseEntity.getStatusCode());
    // }
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }
    
}