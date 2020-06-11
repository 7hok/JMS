package com.java.push.platformSetting;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.java.push.apps.Application;
import com.java.push.platforms.Platform;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;


@Data
@Table(name ="ps_platform_setting")
@Entity
public class PlatformSetting {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @OneToOne(fetch = FetchType.LAZY)
    private Platform platform;
    @ManyToOne
    private Application application;
    private String authorizedKey;
    private String keyId;
    private String teamId;
    private String bundleId;
    private String pushUrl;
    private Character status='1';
    @CreationTimestamp
    private Timestamp registeredAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    public PlatformSetting(Platform platform, Application application, String keyId, String teamId, String bundleId,
            String pushUrl) {
        this.platform = platform;
        this.application = application;
        this.keyId = keyId;
        this.teamId = teamId;
        this.bundleId = bundleId;
        this.pushUrl = pushUrl;
    }

    public PlatformSetting(Platform platform, Application application, String authorizedKey) {
        this.platform = platform;
        this.application = application;
        this.authorizedKey = authorizedKey;
    }
   
    public PlatformSetting(){}
}