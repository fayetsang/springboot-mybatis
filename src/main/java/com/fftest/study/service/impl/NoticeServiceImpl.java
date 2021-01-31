package com.fftest.study.service.impl;

import com.fftest.study.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {

    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    private static final String TOPIC_NOTIFICATION = "notification";

    private static final String GROUP_ID_1 = "GROUP1";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public void sendNotification(String message) {
        if (logger.isDebugEnabled()) {
            logger.debug("send notification to Kafka: {}", message);
        }
        kafkaTemplate.send(TOPIC_NOTIFICATION, message);
    }

    @KafkaListener(topics = TOPIC_NOTIFICATION, groupId = GROUP_ID_1)
    public void getNotification(String message) {
        logger.info("enter getNotification");
        String[] messages = message.split(":");
        logger.info("get message from kafka: id={}, count={}", messages[1], messages[2]);
    }
}
