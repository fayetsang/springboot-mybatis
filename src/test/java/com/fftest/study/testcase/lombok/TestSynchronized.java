package com.fftest.study.testcase.lombok;

import lombok.Synchronized;

public class TestSynchronized implements Runnable {
    @Override
    public void run() {
        count();
    }

    public static int count = 100;

    @Synchronized
    private void sayHello() {
        if (count>0) {
            System.out.println("hello, " + Thread.currentThread().getName() + " count: " + count--);
        }
    }

    public void count() {
        while(count>0) {
            sayHello();
        }
    }

}
