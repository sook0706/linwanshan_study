package com.dorm;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dorm.mapper")
public class DormRepairSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(DormRepairSystemApplication.class, args);
    }
}
