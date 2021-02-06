package com.fftest.study.service.impl;

import com.fftest.study.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    private static final String TOPIC_NOTIFICATION = "notification";

    private static final String GROUP_ID_1 = "GROUP1";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public void sendNotification(String message) {
        if (log.isDebugEnabled()) {
            log.debug("send notification to Kafka: {}", message);
        }
        kafkaTemplate.send(TOPIC_NOTIFICATION, message);
    }

    @KafkaListener(topics = TOPIC_NOTIFICATION, groupId = GROUP_ID_1)
    public void getNotification(String message) {
        log.info("enter getNotification");
        String[] messages = message.split(":");
        log.info("get message from kafka: id={}, count={}", messages[1], messages[2]);
    }
}
