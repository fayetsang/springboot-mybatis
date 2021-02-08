package com.fftest.study.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Counter {

    private String id;
    private Integer count;

}
