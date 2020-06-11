package com.java.push.devices;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.java.push.apps.Application;
import com.java.push.platforms.Platform;
import com.java.push.users.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

// @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@Table(name ="ps_device_client")
@Entity
public class Device {
    @GeneratedValue(strategy = GenerationType.AUTO )
    @Id
    private Integer id;

    private String deviceId;
    private String token;
    
    private String modelName;
    private String os;

    // @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @ManyToOne(fetch = FetchType.EAGER)
    private Application app;
    // @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @ManyToOne(fetch = FetchType.EAGER)
    private Platform platform;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    private Character status='1';

    public Device(String deviceId, String token, Application app, Platform platform, Character status) {
        this.deviceId = deviceId;
        this.token = token;
        this.app = app;
        this.platform = platform;
        this.status = status;
    }

    public Device(){
        
    }

    public Device(String deviceId){
        this.deviceId = deviceId; 
    }

    public Device(String deviceId, String token, Application app, Platform platform) {
        this.deviceId = deviceId;
        this.token = token;
        this.app = app;
        this.platform = platform;
    }
}