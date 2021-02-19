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
    private static final String TOPIC_ALARM = "alarm";


    private static final String NOTIFICATION_GROUP_ID_1 = "NOTIFICATION_GROUP1";
    private static final String NOTIFICATION_GROUP_ID_2 = "NOTIFICATION_GROUP2";
    private static final String NOTIFICATION_GROUP_ID_3 = "NOTIFICATION_GROUP3";

    private static final String ALARM_GROUP_ID_1 = "ALARM_GROUP1";
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public void sendNotification(String message) {
        if (log.isDebugEnabled()) {
            log.debug("send notification to Kafka: {}", message);
        }
        kafkaTemplate.send(TOPIC_NOTIFICATION, message);
    }

    @Override
    public void sendAlarm(String message) {
        if (log.isDebugEnabled()) {
            log.debug("send alarm to Kafka: {}", message);
        }
        kafkaTemplate.send(TOPIC_ALARM, message);
    }

    @KafkaListener(topics = TOPIC_ALARM, containerFactory = "kafkaListenerContainerFactory4Alarm1")
    public void getAlarm(String message){
        log.info("enter getAlarm");
        String[] messages = message.split(":");
        log.info("alarm - get message from kafka: id={}, count={}", messages[1], messages[2]);
    }


    @KafkaListener(topics = TOPIC_NOTIFICATION, containerFactory = "kafkaListenerContainerFactory4NotificationG1")
    public void getNotificationG1(String message){
        printLog(NOTIFICATION_GROUP_ID_1, message);
    }

    @KafkaListener(topics = TOPIC_NOTIFICATION, containerFactory = "kafkaListenerContainerFactory4NotificationG2")
    public void getNotificationG2(String message){
        printLog(NOTIFICATION_GROUP_ID_2, message);
    }

    @KafkaListener(topics = TOPIC_NOTIFICATION, containerFactory = "kafkaListenerContainerFactory4NotificationG3")
    public void getNotificationG3(String message){
        printLog(NOTIFICATION_GROUP_ID_3, message);
    }

    private void printLog(String groupId, String message) {
        log.info("{} - enter getNotification", groupId);
        String[] messages = message.split(":");
        log.info("{} - get message from kafka: id={}, count={}", groupId, messages[1], messages[2]);
    }


}
