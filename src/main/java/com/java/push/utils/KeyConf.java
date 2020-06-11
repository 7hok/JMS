package com.java.push.utils;

public interface KeyConf {
    
    class Notification{
       public static String TITILE = "Push Server";
       public static String MESSAGE = "Hello Menghok";
    }

    class Status {
        public static Character ACTIVE = '1';
        public static Character DISABLED = '9';
    }

    class PlatForm {
        public static String IOS = "1";
        public static String ANDROID = "2";
        public static String WEB= "3";
        public static String P8FILEPATH = "src/main/resources/static/files/";
    }

    class RabbitMQ {
        

        public static String FCMROUTING = "pusher.queue.fcm";
        public static String APNSROUTING ="pusher.queue.apns";
        public static String TOPICROUTING = "pusher.queue.topic";
    }
}