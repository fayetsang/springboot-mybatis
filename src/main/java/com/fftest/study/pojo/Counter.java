package com.fftest.study.pojo;

import java.io.Serializable;

public class Counter implements Serializable {

    static final long serialVersionUID = 1340400917744356654L;

    private String id;
    private Integer count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
