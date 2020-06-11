package com.java.push.devices;

import com.java.push.apps.Application;
import com.java.push.platforms.Platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    
    @Autowired
    DeviceService deviceService;
    
    @PostMapping("/save")
    public Object save(String deviceId,String platformId,String appId,String token){

        Device device = new Device(deviceId,token,new Application(appId),new Platform(platformId));

       return deviceService.saveDevice(device);
    }


}