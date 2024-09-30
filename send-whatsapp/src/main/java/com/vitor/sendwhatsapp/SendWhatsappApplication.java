package com.vitor.sendwhatsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@ComponentScan(basePackages = "com.vitor.sendwhatsapp.*")
@SpringBootApplication
@EnableKafka
@EnableFeignClients
public class SendWhatsappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendWhatsappApplication.class, args);
	}

}
