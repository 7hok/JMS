package com.java.push.notifications.utils;


import com.java.push.utils.HttpClient;
import com.java.push.utils.KeyConf;
// import com.wegarden.notification.model.FirebaseResponse;
// import com.wegarden.notification.services.HeaderRequestInterceptor;

import org.json.JSONObject;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import java.util.ArrayList;
import java.util.HashMap;


public class FirebaseUtil {

    public static final String FIREBASE_SERVER_KEY = "AAAAaO0Mlvo:APA91bECN2HOnEXHEGtqhmZyWZFj7Fo07uYH_nBgii67hR3q51b7qbixv1c4-kFSpCqwwbsjFWsrKUHVflUMO-jUSyld_0TimLUemtoNu2NVT1iXb6YVHdRXbTRbQlCaT_BQ2tnn-bOn";
    public static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    // public static FirebaseResponse getErrorFirebaseResponse() {
    //     FirebaseResponse firebaseResponse = new FirebaseResponse();
    //     firebaseResponse.setMulticast_id(0);
    //     firebaseResponse.setSuccess(0);
    //     firebaseResponse.setCanonical_ids(null);
    //     firebaseResponse.setFailure(1);
    //     firebaseResponse.setResults(null);
    //     return firebaseResponse;
    // }

    public static JSONObject getNotificationJsonForm(HashMap<String, Object> requestBody) throws Exception{

        JSONObject notification = new JSONObject();
        notification.put("title", requestBody.get(KeyConf.Notification.TITILE));
        notification.put("body",  requestBody.get(KeyConf.Notification.MESSAGE));
        notification.put("badge", 1);
        notification.put("sound", "default");
//        notification.put("content-available",1);
        return notification;
    }

    public static JSONObject getNotificationBody(String token, HashMap<String, Object> requestBody) throws Exception {
        JSONObject body = new JSONObject();
        body.put("to", token);
        body.put("priority", "high");
        body.put("notification", getNotificationJsonForm(requestBody));
        body.put("data", getNotificationJsonForm(requestBody));
        return body;
    }

    public static JSONObject getNotificationBody(String token, JSONObject jsonBody) throws Exception {
        JSONObject body = new JSONObject();
        body.put("to", token);
        body.put("priority", "high");
        body.put("notification", jsonBody);
        // body.put("data", getNotificationJsonForm(requestBody));
        return body;
    }

    public static HttpClient getHttpClientWithFirebaseHeader(){
        HttpClient client = new HttpClient();
        client.setHeader("Authorization", "key=" + FIREBASE_SERVER_KEY);
        client.setHeader("Content-Type", "application/json;charset=UTF-8");
        return client;
    }

    public static HttpClient getHttpClientWithFirebaseHeader(String appAuthorizedKey){
        HttpClient client = new HttpClient();
        client.setHeader("Authorization", "key=" + appAuthorizedKey);
        client.setHeader("Content-Type", "application/json;charset=UTF-8");
        return client;
    }
}

