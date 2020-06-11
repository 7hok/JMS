package com.java.push.platformSetting;

import javax.transaction.Transactional;

import com.java.push.utils.FileStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("/apps/platforms/setting")
public class PlatformSettingController {
    
    @Autowired
    PlatformSettingService platformSettingService;

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/save/apns")
    public Object saveApns(String appId,MultipartFile p8file,String fileKey,String teamId,String bundleId) throws Exception{
            String file = FileStorage.uploadFile(p8file);
            return platformSettingService.saveApns(appId, file, fileKey, teamId, bundleId);
    }

    @PostMapping("/save/fcm")
    public Object saveFcm(String appId,String authKey){
        return platformSettingService.saveFcm(appId, authKey);
    }
}