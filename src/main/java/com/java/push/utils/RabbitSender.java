package com.java.push.utils;

import com.java.push.devices.Device;
import com.java.push.history.NotificationHistory;
import com.java.push.messages.APNS;
import com.java.push.messages.FCM;
import com.rabbitmq.client.AMQP.Queue;

import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class RabbitSender {
    
    @Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("${pusher.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${pusher.rabbitmq.routingKey.fcm}")
	private String fcmKey;	
   
	@Value("${pusher.rabbitmq.routingKey.apns}")
	private String apnsKey;	

    @Value("${pusher.rabbitmq.routingKey.topic}")
	private String topicKey;	
	
	@Value("${pusher.rabbitmq.routingKey.history}")
	private String historyKey;	

	
	Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitSender.class);
	
	// @Autowired
	// private Queue apnsQueue;

	// public void sendToFcm(Object object) {
	// 	logger.info("{ Message to "+fcmKey+" }");
	// 	System.out.println(object);
	// 	amqpTemplate.convertAndSend(fcmKey, object);
	// 	// System.out.println("Send msg = " + new Device(32));
	//     // amqpTemplate.send("pusher.queue.fcm", new Device("32"));
	// }

	public void sendToFcm(FCM object) {
		logger.info("{ Request Message to "+fcmKey+" }");
		System.out.println(object);
		amqpTemplate.convertAndSend(fcmKey, object);
	}

	public void sendToApns(APNS apns){
		logger.info("{ Request Message to "+apnsKey+" }");
		System.out.println(apns);
		amqpTemplate.convertAndSend(apnsKey, apns);
	}

	public void sendToTopic(Object object){
		logger.info("{ Request Message to "+topicKey+" }");
		System.out.println(object);
		amqpTemplate.convertAndSend(topicKey, object);
	}

	public void sendToHistoryQueue(NotificationHistory history){
		logger.info("{ Request Message to "+historyKey+" }");
		System.out.println(history);
		amqpTemplate.convertAndSend(historyKey,history);
	}
}