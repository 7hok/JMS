package com.java.push.apps;

import java.util.List;

import com.java.push.utils.KeyConf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    
    @Autowired
    private AppRepository appRepo;

    public Application save(Application app){
        return appRepo.save(app);
    }

    public List<Application> getAllApps(){
        return appRepo.findByStatus(KeyConf.Status.ACTIVE);
    }
    
    public List<Application> getAllAppsByProjectId(Integer id){
        // return appRepo.findByProjectIdAndStatus(id,KeyConf.Status.ACTIVE);
        return null;
    }
}