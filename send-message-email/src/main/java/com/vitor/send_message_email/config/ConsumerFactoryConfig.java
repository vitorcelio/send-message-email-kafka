package com.vitor.send_message_email.config;

import com.vitor.send_message_email.dto.response.EnvioMensagemResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.JsonMessageConverter;

import java.util.HashMap;

@RequiredArgsConstructor
@Configuration
public class ConsumerFactoryConfig {

    private final KafkaProperties properties;

    @Bean
    public ConsumerFactory<String, EnvioMensagemResponseDTO> consumerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EnvioMensagemResponseDTO> concurrentConsumerFactory(ConsumerFactory<String, EnvioMensagemResponseDTO> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, EnvioMensagemResponseDTO>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordMessageConverter(new JsonMessageConverter());
        return factory;
    }

}
