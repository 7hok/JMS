package com.java.push.users;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@Data
@Table(name ="ps_user")
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.AUTO )
    @Id
    private String id;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    private Character status ='1';

    public User(String id) {
        this.id = id;
    }

    public User() {
     
    }

}