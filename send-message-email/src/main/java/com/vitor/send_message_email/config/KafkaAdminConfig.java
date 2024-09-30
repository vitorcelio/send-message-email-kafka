package com.vitor.send_message_email.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

import java.util.HashMap;

@RequiredArgsConstructor
@Configuration
public class KafkaAdminConfig {

    private final KafkaProperties properties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopics topics() {
        return new NewTopics(TopicBuilder.name("envio-mensagem").partitions(2).replicas(1).build());
    }


}
