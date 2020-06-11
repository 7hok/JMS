
package com.java.push.history;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.java.push.apps.Application;
import com.java.push.users.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

/**
 * Notification
 */
@Data
@Table(name ="ps_history")
@Entity
public class NotificationHistory {
    @GeneratedValue(strategy = GenerationType.AUTO )
    @Id
    private Integer id;
    private String appId;
    private String recieverId;
    private String title;
    private String message;
    @Column(length = 8)
    private String toPlatform;
    @Column(length = 8)
    private String status;
    private String responseMsg;
    @CreationTimestamp
    private Timestamp createdAt;

    public NotificationHistory(String appId, String recieverId, String title, String message) {
        this.appId = appId;
        this.recieverId = recieverId;
        this.title = title;
        this.message = message;
    }

    public NotificationHistory(){}

    public NotificationHistory(String appId, String recieverId, String title, String message, String toPlatform,
            String status,String responseMsg) {
        this.appId = appId;
        this.recieverId = recieverId;
        this.title = title;
        this.message = message;
        this.toPlatform = toPlatform;
        this.status = status;
        this.responseMsg = responseMsg;
    }

}