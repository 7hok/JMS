package com.java.push.notifications.utils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLException;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;
import com.eatthepath.pushy.apns.auth.ApnsSigningKey;
import com.eatthepath.pushy.apns.util.ApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.eatthepath.pushy.apns.util.TokenUtil;

/**
 * Document : https://pushy-apns.org/
 */
public class APNsUtil {

    public static SimpleApnsPushNotification getSimpleApns(String userToken,String topic, ApnsPayloadBuilder payloadBuilder ) {
      
        final String payload = payloadBuilder.build();
        final String token = TokenUtil.sanitizeTokenString(userToken);

        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token,topic, payload);
        return pushNotification;
    }

    public static SimpleApnsPushNotification getSimpleApnsWithPayAsString(String userToken,String topic,String payload ) {
      
        final String token = TokenUtil.sanitizeTokenString(userToken);

        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token,topic, payload);
        return pushNotification;
    }

    public static ApnsClient getApnsCredentials(String pFileDirectory, String teamId, String keyString)
            throws InvalidKeyException, SSLException, NoSuchAlgorithmException, IOException {
         ApnsClient apnsClient = new ApnsClientBuilder()
        .setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST)
        .setSigningKey(ApnsSigningKey.loadFromPkcs8File(new File(pFileDirectory),teamId,keyString))
        .build();
        return apnsClient;
    }

   
}