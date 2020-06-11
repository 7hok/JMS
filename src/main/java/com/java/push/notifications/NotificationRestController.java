package com.java.push.notifications;

import java.util.List;

import com.java.push.devices.Device;
import com.java.push.devices.DeviceService;
import com.java.push.messages.APNS;
import com.java.push.messages.FCM;
import com.java.push.platformSetting.PlatformSetting;
import com.java.push.platformSetting.PlatformSettingService;
import com.java.push.utils.KeyConf;
import com.java.push.utils.RabbitSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationRestController {
   
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DeviceService deviceService;
   
    @Autowired
    private PlatformSettingService settingService;

    @Autowired 
    private RabbitSender rabbitSender;

    Logger logger = LoggerFactory.getLogger(NotificationRestController.class);
    
    @PostMapping("/home")
    public Object subscribe(String app_id, String push_id, String model_name,String platform_code , String company_id){
        return null;
    }

    @PostMapping("/send/single")
    public Object send(String app_id, String receiver_id, String title,String message) throws Exception{
        System.out.println(app_id + receiver_id);
        Device device = deviceService.getActiveDeviceByDeviceIdAndAppId(receiver_id,app_id);
        PlatformSetting deviceSetting = settingService.getActivePlatformConfiguredByAppIdAndPlatFormId(app_id, device.getPlatform().getId());
        String response = null;
        if(device.getPlatform().getId().equals(KeyConf.PlatForm.IOS)){
            String p8file = deviceSetting.getPushUrl();
            String teamId = deviceSetting.getTeamId();
            String fileKey = deviceSetting.getKeyId();
            String groupId = deviceSetting.getBundleId();
            response=   notificationService.sendNotificationToIOS(KeyConf.PlatForm.P8FILEPATH+p8file, teamId, fileKey, groupId, device.getToken(), title, message);
        }else if(KeyConf.PlatForm.ANDROID.equals(deviceSetting.getPlatform().getId()) ){
            response= notificationService.sendNotificationToFCM(deviceSetting.getAuthorizedKey(), device.getToken(),title,message);
        }
       
        logger.info("[Device Response]");
        // logger.info(device.toString());

        return response;
    }
    /**
     * 
     * @param app_id
     * @param title
     * @param message
     * @return
        Query platformSetting by appid
        Query Devices
     */
    @PostMapping("/send/all")
    public Object sendToAll (String app_id, String title,String message){ 
    
        List<PlatformSetting> platformSettings = settingService.getActivePlatformConfiguredByAppId(app_id);
        List<Device> androids = deviceService.getActiveDeviceByAppIdAndPlatformId(app_id,KeyConf.PlatForm.ANDROID);
        List<Device> ios = deviceService.getActiveDeviceByAppIdAndPlatformId(app_id,KeyConf.PlatForm.IOS);
        List<Device> browsers = deviceService.getActiveDeviceByAppIdAndPlatformId(app_id, KeyConf.PlatForm.WEB);
        
        logger.info("[Platform with App Id "+app_id+" ]");
        platformSettings.forEach(platformSetting -> {
            logger.info("[Platform Information Below ]");
            System.out.println(platformSetting);
            if(KeyConf.PlatForm.ANDROID.equals(platformSetting.getPlatform().getId()) ){
             
                logger.info("[Android Starts Pushing ]");
                androids.forEach(android -> {
                    logger.info(android.toString());

                    rabbitSender.sendToFcm(new FCM(platformSetting.getAuthorizedKey(),android.getToken(),title,message));
                //    notificationService.sendNotificationToFCM(platformSetting.getAuthorizedKey(), android.getToken());
                });
            }else if(KeyConf.PlatForm.IOS.equals(platformSetting.getPlatform().getId())){
                String p8file = platformSetting.getPushUrl();
                String teamId = platformSetting.getTeamId();
                String fileKey = platformSetting.getKeyId();
                String groupId = platformSetting.getBundleId();
                logger.info("[IOS Starts Pushing ]");
                ios.forEach(device -> {
                    logger.info(device.toString());
                    try {
                        //  notificationService.sendNotificationToIOS(KeyConf.PlatForm.P8FILEPATH+p8file, teamId, fileKey, groupId, device.getToken(), title, message);
                        rabbitSender.sendToApns(new APNS(KeyConf.PlatForm.P8FILEPATH+p8file,teamId,fileKey,groupId,device.getToken(),title,message));
                    } catch (Exception e) {
                       logger.info("ERROR OCUR DURING REQUEST ");
                       System.out.println(e.getMessage());
                    }
                   
                });
            }else if(KeyConf.PlatForm.WEB.equals(platformSetting.getPlatform().getId())){
                logger.info("[Browser Starts Pushing ]");
                browsers.forEach(browser -> {
                    logger.info(browser.toString());
                //    notificationService.sendNotificationToFCM(platformSetting.getAuthorizedKey(), browser.getToken());
                   rabbitSender.sendToFcm(new FCM(platformSetting.getAuthorizedKey(),browser.getToken(),title,message));
                });
            }
            // logger.info( );
        });

        // logger.info("[Android Devices with App Id "+app_id+" ]");
        // androids.forEach(android -> {
        //     logger.info(android.toString());
        //     // notificationService.sendNotificationToAndroid(android, userToken)
        // });

      
        return title+message;
    }

    // @GetMapping("/send/fcm/{msg}")
    // public String sentFCM(@PathVariable("msg") String msg){
       
    //     rabbitSender.sendToFcm(msg);
      
       
    //     return "lets ckeck";
    // }
    
    // @GetMapping("/send/apns/{msg}")
    // public String sentAPNS(@PathVariable("msg") String msg){
    //     rabbitSender.sendToApns(msg);
    //     return "lets ckeck";
    // }
}