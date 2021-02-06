package com.fftest.study.service;


public interface CounterService {

    Integer getCounter(String id);

    void increaseCounter(String id);

    void deleteCounter(String id);
}
