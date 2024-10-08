package com.vitor.send_message_email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SendMessageEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SendMessageEmailApplication.class, args);
    }

}
