package com.quaero.quaerosmartplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
@MapperScan("com.quaero.quaerosmartplatform.domain.mapper")
public class QuaeroSmartPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuaeroSmartPlatformApplication.class, args);
    }

}
