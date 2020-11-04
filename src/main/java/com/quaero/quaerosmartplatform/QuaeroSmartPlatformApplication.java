package com.quaero.quaerosmartplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.quaero.quaerosmartplatform.domain.mybatis.mapper")
public class QuaeroSmartPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuaeroSmartPlatformApplication.class, args);
    }

}
