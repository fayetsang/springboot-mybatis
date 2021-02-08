package com.fftest.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fftest.study.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication =
                new SpringApplication(Application.class);

        springApplication.setBannerMode(Banner.Mode.OFF);

        springApplication.run(args);

    }
}
