package com.woowacourse.kkogkkog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KkogkkogApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkogkkogApplication.class, args);
    }

}
