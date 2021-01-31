package com.fftest.study.service;


import com.fftest.study.pojo.Counter;

public interface CounterService {

    Integer getCounter(String id);

    void increaseCounter(String id);

    void deleteCounter(String id);
}
