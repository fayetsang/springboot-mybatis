package com.fftest.study.testcase;

import lombok.extern.log4j.Log4j;
import org.junit.Ignore;
import org.junit.Test;

@Log4j
public class LombokLogTest {

    public void print() {
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
        System.out.println("hello world");
    }

    @Test
    @Ignore // manual test, modify the log level in log4j.properties
    public void testLog() {
        print();
    }
}
