package com.fftest.study.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerKafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServer;

    private static final String NOTIFICATION_GROUP_ID_1 = "NOTIFICATION_GROUP1";
    private static final String NOTIFICATION_GROUP_ID_2 = "NOTIFICATION_GROUP2";
    private static final String NOTIFICATION_GROUP_ID_3 = "NOTIFICATION_GROUP3";

    private static final String ALARM_GROUP_ID_1 = "ALARM_GROUP1";


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory4Alarm1() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory4Alarm1());
        factory.setConcurrency(2);
        factory.getContainerProperties().setPollTimeout(4000);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory4NotificationG1() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory4Notification1());
        factory.setConcurrency(1);
        factory.getContainerProperties().setPollTimeout(4000);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory4NotificationG2() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory4Notification2());
        factory.setConcurrency(1);
        factory.getContainerProperties().setPollTimeout(4000);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory4NotificationG3() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory4Notification3());
        factory.setConcurrency(1);
        factory.getContainerProperties().setPollTimeout(4000);
        return factory;
    }

    public Map<String, Object> getCommonProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }

    public ConsumerFactory<String, String> consumerFactory4Alarm1() {
        Map<String, Object> properties = getCommonProperties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, ALARM_GROUP_ID_1);
        return new DefaultKafkaConsumerFactory<>(properties);
    }



    public ConsumerFactory<String, String> consumerFactory4Notification1() {
        Map<String, Object> properties = getCommonProperties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, NOTIFICATION_GROUP_ID_1);
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    public ConsumerFactory<String, String> consumerFactory4Notification2() {
        Map<String, Object> properties = getCommonProperties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, NOTIFICATION_GROUP_ID_2);
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    public ConsumerFactory<String, String> consumerFactory4Notification3() {
        Map<String, Object> properties = getCommonProperties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, NOTIFICATION_GROUP_ID_3);
        return new DefaultKafkaConsumerFactory<>(properties);
    }

}
