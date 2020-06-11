package com.java.push.notifications;

import java.io.File;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;
import com.eatthepath.pushy.apns.PushNotificationResponse;
import com.eatthepath.pushy.apns.auth.ApnsSigningKey;
import com.eatthepath.pushy.apns.util.ApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.eatthepath.pushy.apns.util.TokenUtil;
import com.eatthepath.pushy.apns.util.concurrent.PushNotificationFuture;
import com.java.push.devices.DeviceService;
import com.java.push.notifications.utils.APNsUtil;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public class NotificationController {
    
    Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/home")
    public String home(){
        notificationService.sendNotification();
        return "welcome";
    }

    @GetMapping("/send/{userId}")
    public String send(@PathVariable("userId")Integer userId){
        // notificationService.sendNotification(appAuthorizedKey, userToken);
        logger.info("[Getting Data From Device]");
        
        // notificationService.sendNotificationByUserId(userId);

        return "sent";
    }
    
    @GetMapping("/fcm/send")
    public String send(String token){
        // notificationService.sendNotification(appAuthorizedKey, userToken);
        logger.info("[Getting Data From Device]");
        
        // notificationService.sendNotificationByUserId(userId);

        return "sent";
    }
   
    @GetMapping("/send/ios")
    public String ios() throws Exception{
        final ApnsClient apnsClient = APNsUtil.getApnsCredentials("src/main/resources/static/files/a24643db-072f-47fa-aa59-dc9c1dc79ab9.p8","62EMNW6G6D", "A7L9UC597M");
        
        ApnsPayloadBuilder payloadBuilder = new SimpleApnsPayloadBuilder();
        payloadBuilder.setAlertBody("Good Evening");
        payloadBuilder.setAlertTitle("Welcome To PUSHER");
        final String payload = payloadBuilder.build();
        
        logger.info("[ Request Payload From APNs]");
        logger.info(payload);
        
        SimpleApnsPushNotification pushNotification =  APNsUtil.getSimpleApnsWithPayAsString("cd49303111aafa3c05d72b1d6e5a54554c1d21201891a8306d4a8d97aa622d8b","kkh.com.kosignpush.test", payload);
       
        PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>>
         sendNotificationFuture = apnsClient.sendNotification(pushNotification);
        
        logger.info("[ Response Data From APNs]");
        if(sendNotificationFuture.get().isAccepted()){
            logger.info("[Sucessfully Response]");
            System.out.println(sendNotificationFuture.get().toString());
        }else{
            logger.info("[Error Response]");
            System.out.println(sendNotificationFuture.get().getRejectionReason().toString());
        }
        
        
         return sendNotificationFuture.get().toString();
    }
}