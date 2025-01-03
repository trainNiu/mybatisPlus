package com.example.aicaimp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.example.aicaimp", "com.example.aicaiframework"})
@MapperScan(basePackages = {"com.example.aicaimp.mapper"})
public class AicaiMpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AicaiMpApplication.class, args);
    }

}
