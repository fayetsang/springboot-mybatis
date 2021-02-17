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
    private static final String GROUP_ID_2 = "GROUP2";
    private static final String GROUP_ID_3 = "GROUP3";

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
    public void getNotificationG1(String message) {
        log.info("G1 - enter getNotification");
        String[] messages = message.split(":");
        log.info("G1 - get message from kafka: id={}, count={}", messages[1], messages[2]);
    }

    @KafkaListener(topics = TOPIC_NOTIFICATION, groupId = GROUP_ID_2)
    public void getNotificationG2(String message) {
        log.info("G2 - enter getNotification");
        String[] messages = message.split(":");
        log.info("G2 - get message from kafka: id={}, count={}", messages[1], messages[2]);
    }

    @KafkaListener(topics = TOPIC_NOTIFICATION, groupId = GROUP_ID_3)
    public void getNotificationG3(String message) {
        log.info("G3 - enter getNotification");
        String[] messages = message.split(":");
        log.info("G3 - get message from kafka: id={}, count={}", messages[1], messages[2]);
    }

}
