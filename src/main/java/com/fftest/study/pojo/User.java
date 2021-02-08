package com.fftest.study.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@TableName("user")
@NoArgsConstructor
public class User{

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;
    @TableField("name")
    private String name;
    @TableField("status")
    private String status;

    public User(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public User(String name, String status) {
        this.name = name;
        this.status = status;
    }

}
