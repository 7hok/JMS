package com.java.push.devices;

import java.util.List;

import com.java.push.utils.KeyConf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private Logger logger = LoggerFactory.getLogger(DeviceService.class);
    @Autowired
    private DeviceRepository deviceRepo;
    
    public List<Device> getDevicesByUserId(Integer userId){
    //    return deviceRepo.findByUserIdAndStatus(userId, KeyConf.Status.ACTIVE);
        return null;
    }

    public Device getActiveDeviceByDeviceIdAndAppId(String deviceId,String appId){
        logger.info("[ Request To Repository ]");
        Device device = deviceRepo.findByDeviceIdAndAppId(deviceId,appId);
        System.out.println(device);
        // if(device.getStatus().equals(KeyConf.Status.DISABLED)){
        //     return null;
        // }
        return device;
    }


    public List<Device> getActiveDeviceByAppId (String appId) {
        List<Device> devices= deviceRepo.findByAppIdAndStatus(appId,KeyConf.Status.ACTIVE);
        return devices;
    }

    public List<Device> getActiveDeviceByAppIdAndPlatformId(String appId,String platFormId){
        List<Device> devices= deviceRepo.findByAppIdAndPlatformIdAndStatus(appId,platFormId,KeyConf.Status.ACTIVE);
        return devices;
    }

    public Device saveDevice(Device device){
        Device _device = deviceRepo.save(device);

        return _device;
    }
}