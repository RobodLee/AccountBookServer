package com.robod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 李迪
 * @date 2020/3/7 10:57
 */
@SpringBootApplication
@EnableTransactionManagement
public class AccountBookSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountBookSpringBootApplication.class);
    }

}
