package com.fftest.study.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class User implements Serializable {

    static final long serialVersionUID = 2372405317744358833L;

    private Long id;
    private String name;
    private String status;

}
