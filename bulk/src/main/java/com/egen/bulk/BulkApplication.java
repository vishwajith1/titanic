package com.egen.bulk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.egen.bulk"})
@EnableScheduling
public class BulkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BulkApplication.class, args);
    }

}
