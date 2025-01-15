package com.example.aicaiuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.example.aicaiuser", "com.example.aicaiframework"})
@MapperScan(basePackages = {"com.example.aicaiuser.mapper"})
public class AicaiUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AicaiUserApplication.class, args);
    }

}
