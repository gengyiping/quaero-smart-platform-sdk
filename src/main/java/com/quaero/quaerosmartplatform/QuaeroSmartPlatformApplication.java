package com.quaero.quaerosmartplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching //开启缓存
@MapperScan("com.quaero.quaerosmartplatform.domain.mapper")
@EnableScheduling
public class QuaeroSmartPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuaeroSmartPlatformApplication.class, args);
    }

}
