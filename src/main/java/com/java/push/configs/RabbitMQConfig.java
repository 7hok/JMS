package com.java.push.configs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // @Value("${pusher.rabbitmq.fcm.queue}")
	String fcmQueueName="pusher.queue.fcm";
	
	// @Value("${pusher.rabbitmq.apns.queue}")
	String apnsQueueName="pusher.queue.apns";
	
	@Value("${pusher.rabbitmq.queue.history}")
	String historyQueueName;

	@Value("${pusher.rabbitmq.exchange}")
	String exchange;

	String exchange2= "pusher.direct.apns.exchange";

	// @Value("${pusher.rabbitmq.routingkey}")
	// private String routingkey;

	@Bean
	Queue fcmQueue() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-queue-mode", "lazy");
		// args.put("x-max-length", 10);
		// args.put("x-overflow", "reject-publish");
		return new Queue(fcmQueueName, false,false,false,args);
	}

	@Bean
	Queue apnsQueue() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-queue-mode", "lazy");
		return new Queue(apnsQueueName, false,false,false,args);
	}
	
	// @Bean
	// Queue historyQueue() {
	// 	Map<String, Object> args = new HashMap<String, Object>();
	// 	args.put("x-queue-mode", "lazy");
	// 	return new Queue(historyQueueName, false,false,false,args);
	// }

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	// @Bean
	// DirectExchange exchange2() {
	// 	return new DirectExchange(exchange2);
	// }
	// @Bean
	// Binding binding1(DirectExchange exchange) {
	// 	return BindingBuilder
	// 			.bind(fcmQueue()).to(exchange()).with(fcmQueue().getName());
	// }
	// @Bean
	// Binding binding2(DirectExchange exchange) {
	// 	return BindingBuilder
	// 			.bind(apnsQueue()).to(exchange2() ).with(apnsQueue().getName());
	// }

	@Bean
    public Declarables topicBindings() {


		return new Declarables(
			BindingBuilder
				.bind(fcmQueue())
				.to(exchange())
				.with(fcmQueue().getName()),
		    BindingBuilder
				.bind(apnsQueue())
				.to(exchange())
				.with(fcmQueue().getName())
			// ,BindingBuilder
			// 	.bind(historyQueue())
			// 	.to(exchange())
			// 	.with(historyQueue().getName())	
				
				);
    }


	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}