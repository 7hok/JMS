package com.java.push.notifications;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLException;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.PushNotificationResponse;
import com.eatthepath.pushy.apns.util.ApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.eatthepath.pushy.apns.util.concurrent.PushNotificationFuture;
import com.java.push.devices.Device;
import com.java.push.devices.DeviceService;
import com.java.push.history.NotificationHistory;
import com.java.push.history.NotificationHistoryService;
import com.java.push.messages.APNS;
import com.java.push.messages.FCM;
import com.java.push.notifications.utils.APNsUtil;
import com.java.push.notifications.utils.FirebaseUtil;
import com.java.push.users.User;
import com.java.push.utils.HttpClient;
import com.java.push.utils.KeyConf;
import com.java.push.utils.RabbitSender;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * NotificationService
 */
@Service
public class NotificationService {

    Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationHistoryService historyService;
    
    @Autowired
    private DeviceService deviceService;
    @Autowired
    RabbitSender rabbitSender;
    
    

    public static String myKey = "c7lEKFLhByY:APA91bEl6tzsfSkBxEsrXH8LaCT3MtdRii5zgEPisJ0isAUBnE9x5RoN3k4yAuWi-eRo_-isMdoebmqiWMDpn-S9wwROVAIMFxbwsYOO53hDXRGab86xwqIWBtI-D6CVbWqOFWDXv_kp";

    public void sendNotification() {
        HttpClient httpClient = FirebaseUtil.getHttpClientWithFirebaseHeader();
        JSONObject json = new JSONObject();
        json.put("body", "Body of Your Notification");
        json.put("title", "Good morning Menghok");

        try {
            JSONObject notificatedService = FirebaseUtil.getNotificationBody(myKey, json);
            httpClient.post(FirebaseUtil.FIREBASE_API_URL, notificatedService.toString());
        } catch (Exception e) {
            logger.info("ERROR FROM SERVICE");
            logger.info(e.getMessage());
        }

    }


    public String sendNotificationToFCM(String appAuthorizedKey, String userToken,String title ,String message) {
        HttpClient httpClient = FirebaseUtil.getHttpClientWithFirebaseHeader(appAuthorizedKey);

        JSONObject json = new JSONObject();
        json.put("body", message);
        json.put("title", title);

        try {

            JSONObject notificatedService = FirebaseUtil.getNotificationBody(userToken, json);

            logger.info("[Request To Firebase]");
            logger.info(notificatedService.toString());

            JSONObject jsonResult = httpClient.post(FirebaseUtil.FIREBASE_API_URL, notificatedService.toString());
            logger.info("[Response From Firebase]");
            logger.info(jsonResult.toString());
            
            Integer result = (Integer)jsonResult.get("success");
            org.json.JSONArray strResult = (org.json.JSONArray)jsonResult.get("results");
            historyService.saveHistory(new NotificationHistory(appAuthorizedKey, userToken, title, message,KeyConf.PlatForm.ANDROID,result.toString(),strResult.toString()));
            return jsonResult.toString();
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("ERROR FROM SERVICE");
            logger.info(e.getMessage());
            historyService.saveHistory(new NotificationHistory(appAuthorizedKey, userToken, title, message,KeyConf.PlatForm.ANDROID,"0",e.getMessage()));
            return e.getMessage();
        }

    }

 
    public String sendNotificationToIOS(String p8file,String teamId,String fileKey,String bundleId,String token,String msgTitle,String msgBody) throws InterruptedException, ExecutionException, InvalidKeyException,
            SSLException, NoSuchAlgorithmException, IOException {
        final ApnsClient apnsClient = APNsUtil.getApnsCredentials(p8file,teamId,fileKey);
        
        ApnsPayloadBuilder payloadBuilder = new SimpleApnsPayloadBuilder();
        payloadBuilder.setAlertBody(msgBody);
        payloadBuilder.setAlertTitle(msgTitle);
        final String payload = payloadBuilder.build();
        
        logger.info("[ Request Payload From APNs]");
        logger.info(payload);
        
        SimpleApnsPushNotification pushNotification =  APNsUtil.getSimpleApnsWithPayAsString(token,bundleId, payload);
       
        PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>>
         sendNotificationFuture = apnsClient.sendNotification(pushNotification);
        
        logger.info("[ Response Data From APNs]");
       
        String responseStatus = "";
        String responseMsg = "";
        if(sendNotificationFuture.get().isAccepted()){
            logger.info("[Sucessfully Response]");
            System.out.println(sendNotificationFuture.get().toString());
            responseStatus = "true";
            responseMsg  = sendNotificationFuture.get().getApnsId().toString();
        }else{
            logger.info("[Error Response]");
            System.out.println(sendNotificationFuture.get().getRejectionReason());
            responseStatus = "false";
            responseMsg = sendNotificationFuture.get().getRejectionReason();
        }
        historyService.saveHistory(new NotificationHistory(token, token, msgTitle, msgBody,KeyConf.PlatForm.IOS,responseStatus,responseMsg));
        
         return sendNotificationFuture.get().toString();
    }
    public void sendNotificationByUserId(Integer userId){
        deviceService.getDevicesByUserId(userId).forEach(device -> {
            System.out.println(device);
            // String requestNotificationBody = this.sendNotification(device.getApp().get(), device.getToken());
            // historyService.saveHistory(new NotificationHistory(requestNotificationBody,new User(userId)));
        });

    }
    @RabbitListener(queues = "pusher.queue.fcm")
    public void sendNotificationByFCM(FCM fcm){
        System.out.println("Response Message from " + fcm);
        this.sendNotificationToFCM(fcm.authorizedKey,fcm.token,fcm.title,fcm.message);
    }

    @RabbitListener(queues = "pusher.queue.fcm")
    public void sendNotificationByFCMSupport(FCM fcm){
        System.out.println("Response Message from " + fcm);
        this.sendNotificationToFCM(fcm.authorizedKey,fcm.token,fcm.title,fcm.message);
    }

    @RabbitListener(queues = "pusher.queue.apns")
    public void sendNotificationByAPNS(APNS apns) {
        System.out.println("Response Message from  " + apns);
        try {
             this.sendNotificationToIOS(apns.p8file, apns.teamId, apns.fileKey, apns.bundleId, apns.token, apns.title, apns.message);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }     
    @RabbitListener(queues = "pusher.queue.apns")
    public void sendNotificationByAPNSSupport(APNS apns)  {
        System.out.println("Response Message from " + apns);
        // System.out.println("Message read from myQueue APNS: " + apns);
        try {
             this.sendNotificationToIOS(apns.p8file, apns.teamId, apns.fileKey, apns.bundleId, apns.token, apns.title, apns.message);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    // @RabbitListener(queues = "pusher.queue.apns")
    // public void sendNotificatisdaonByAPNS(String token){
    //     System.out.println("Message read from myQueuasdasdasdasdsade APNS: " + token);
    // }
}