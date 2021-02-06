package com.fftest.study.controller;

import com.fftest.study.service.CounterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.function.Consumer;

@Controller
@RequestMapping("/counter")
@Slf4j
public class CounterController {

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
            log.error("exception happen while getCounterById", e);
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
            log.error("exception happen while modifyCounter", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
       return status;
    }

}
