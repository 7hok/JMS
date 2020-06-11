package com.java.push.platformSetting;

import java.util.List;

import com.java.push.apps.Application;
import com.java.push.utils.GlobalMethod;
import com.java.push.utils.KeyConf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatformSettingService {
    
    @Autowired
    private PlatformSettingRepository settingRepo;

    Logger logger = LoggerFactory.getLogger(PlatformSettingService.class);

    public List<PlatformSetting> getActivePlatformConfiguredByAppId(String appId) {
        List<PlatformSetting> platforms = settingRepo.findByApplicationIdAndStatus(appId,KeyConf.Status.ACTIVE);
        return platforms;
    }
    
    public PlatformSetting getActivePlatformConfiguredByAppIdAndPlatFormId(String appId,String platformId){
        PlatformSetting platform = settingRepo.findByApplicationIdAndPlatformId(appId,platformId);
        if(platform == null | platform.getStatus().equals(KeyConf.Status.DISABLED)){
            return null;
        }
        return platform;
    }

    public PlatformSetting saveApns(String appId, String file, String fileKey, String teamId, String bundleId){
        PlatformSetting _platformSetting = new PlatformSetting(GlobalMethod.getIos(),new Application(appId),fileKey,teamId,bundleId,file);
        return settingRepo.save(_platformSetting);
    }

    public PlatformSetting saveFcm(String appId,  String authKey){
        PlatformSetting _platformSetting = new PlatformSetting(GlobalMethod.getAndroid(),new Application(appId),authKey);
        return settingRepo.save(_platformSetting);
    }

	// public Object saveApns(String appId, String file, String fileKey, String teamId, String bundleId) {
	// 	return null;
	// }

}