package com.fftest.study.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Counter implements Serializable {

    static final long serialVersionUID = 1340400917744356654L;

    private String id;
    private Integer count;

}
