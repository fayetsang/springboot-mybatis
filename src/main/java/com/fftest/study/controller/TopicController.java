package com.fftest.study.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/topic")
@Slf4j
public class TopicController {

    @Autowired
    private AdminClient adminClient;

    private static Gson gson = new Gson();

    @PostMapping
    public ResponseEntity<Void> createTopic(@RequestParam("topicName") String topicName,
                                            @RequestParam("partitions") Integer partitions,
                                            @RequestParam("replicationFactor") Short replicationFactor) {
        HttpStatus status = null;
        try {
            if (StringUtils.isBlank(topicName) || partitions == null || replicationFactor == null) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                log.info("create topic, topicName={}, partitions={}, replicationFactor={}",
                        topicName, partitions, replicationFactor);
                adminClient.createTopics(Arrays.asList(new NewTopic(topicName, partitions, replicationFactor)));
                status = HttpStatus.CREATED;
            }
        } catch (Exception e) {
            log.error("exception happen while createTopic", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).build();
    }

    @GetMapping
    public ResponseEntity<String> getTopics() {
        HttpStatus status = null;
        List<String> resultList = new ArrayList<>();
        try {
            ListTopicsResult result = adminClient.listTopics();
            Collection<TopicListing> list = result.listings().get();
            for (TopicListing topicListing : list) {
                resultList.add(topicListing.name());
            }
            status = HttpStatus.OK;
        } catch (Exception e) {
            log.error("exception happen while getTopics", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(gson.toJson(resultList));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTopic(@RequestParam("topicName") String topicName) {
        HttpStatus status = null;
        try {
            if (StringUtils.isBlank(topicName)) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                log.info("delete topic: {}", topicName);
                adminClient.deleteTopics(Arrays.asList(topicName));
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            log.error("exception happen while getTopics", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).build();
    }

}
