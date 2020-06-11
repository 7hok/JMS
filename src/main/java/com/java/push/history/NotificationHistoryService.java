package com.java.push.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationHistoryService {
    
    @Autowired
    NotificationHistoryRepository historyRepo; 

    private Logger logger = LoggerFactory.getLogger(NotificationHistoryService.class);

    public void saveHistory (NotificationHistory history){
        historyRepo.save(history);
    }

    // @RabbitListener(queues = "pusher.queue.history")
    public void saveHistoryWithRabbit(NotificationHistory history){
        logger.info("{ Response from History Queue  }");
        historyRepo.save(history);
    }
}