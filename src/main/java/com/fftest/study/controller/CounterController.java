package com.fftest.study.controller;

import com.fftest.study.service.CounterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.function.Consumer;

@Controller
@RequestMapping("/counter")
public class CounterController {

    private final static Logger logger = LoggerFactory.getLogger(CounterController.class);

    @Autowired
    private CounterService counterService;

    @GetMapping("/{id}")
    public ResponseEntity<Integer> getCounterById(@PathVariable("id") String id) {
        HttpStatus status = null;
        Integer counter = null;
        try {
            counter = counterService.getCounter(id);
            status = HttpStatus.OK;
        }catch (Exception e) {
            logger.error("exception happen while getCounterById", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(counter);
    }

    @PostMapping
    public ResponseEntity<Void> increaseCounter(String id) {
        HttpStatus status = modifyCounter((str) -> counterService.increaseCounter(str), id);
        return ResponseEntity.status(status).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCounter(String id) {
        HttpStatus status = modifyCounter((str) -> counterService.deleteCounter(str), id);
        return ResponseEntity.status(status).build();
    }

    private HttpStatus modifyCounter(Consumer<String> consumer, String id) {
        HttpStatus status = null;
        try {
            if (StringUtils.isBlank(id)) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                consumer.accept(id);
                status = HttpStatus.NO_CONTENT;
            }
        }catch (Exception e) {
            logger.error("exception happen while modifyCounter", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
       return status;
    }

}
