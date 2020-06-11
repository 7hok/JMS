package com.java.push.utils;

import com.java.push.platforms.Platform;

public class GlobalMethod {
    
   public static Platform getAndroid () {
      return new Platform(KeyConf.PlatForm.ANDROID);
   }
   public static Platform getIos () {
    return new Platform(KeyConf.PlatForm.IOS);
 }
  
 public static Platform getBrowser() {
    return new Platform(KeyConf.PlatForm.WEB);
 }
}