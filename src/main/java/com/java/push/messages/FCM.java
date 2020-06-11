package com.java.push.messages;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class FCM {
   public String authorizedKey;
   public String token;
   public String title;
   public String message;

   public FCM(String authorizedKey, String token, String title, String message) {
       this.authorizedKey = authorizedKey;
       this.token = token;
       this.title = title;
       this.message = message;
   }

   public FCM(){}
}