package com.fftest.study.service.impl;

import com.fftest.study.service.CounterService;
import com.fftest.study.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CounterServiceImpl implements CounterService {

    private static final Logger logger = LoggerFactory.getLogger(CounterServiceImpl.class);

    private static final Integer RAISE_NOTIFICATION_THRESHOLD = 3;

    private static final Integer COUNTER_EXPIRE_MINS = 5;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    NoticeService noticeService;

    @Override
    public Integer getCounter(String id) {
        if (logger.isDebugEnabled()) {
            logger.debug("enter getCounter, id: {}", id);
        }
        Integer count = (Integer) redisTemplate.opsForValue().get(id);
        return count!=null?count:0;
    }

    @Override
    public void increaseCounter(String id) {
        if (logger.isDebugEnabled()) {
            logger.debug("enter increaseCounter, id: {}", id);
        }
        Integer count = getCounter(id);
        redisTemplate.opsForValue().set(id, ++count);
        redisTemplate.expire(id, Duration.ofMinutes(COUNTER_EXPIRE_MINS));

        if (count >= RAISE_NOTIFICATION_THRESHOLD) {
            if (logger.isDebugEnabled()) {
                logger.debug("d: {}, count: {}, begin to raise notification.", id, count);
            }
            noticeService.sendNotification("NOTIFICATION:"+id+":"+count);
        }
    }

    @Override
    public void deleteCounter(String id) {
        if (logger.isDebugEnabled()) {
            logger.debug("enter deleteCounter, id: {}", id);
        }
        redisTemplate.delete(id);
    }

}
