package com.vitor.send_message_email.listener;

import com.vitor.send_message_email.dto.response.EnvioMensagemResponseDTO;
import com.vitor.send_message_email.services.EnvioMensagemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class ConsumerListener {

    private final EnvioMensagemService service;

    @KafkaListener(groupId = "grupo-whatsapp", topics = "response-whatsapp", containerFactory = "concurrentConsumerFactory")
    public void whatsapp(@Payload EnvioMensagemResponseDTO envioMensagem) {
        log.info("Recebi a resposta do envio de mensagens do Whatsapp: {}", envioMensagem.toString());
        service.update(envioMensagem);
    }

}
