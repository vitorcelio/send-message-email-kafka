package com.vitor.sendemail.listener;

import com.vitor.sendemail.dto.EnvioMensagemResponseDTO;
import com.vitor.sendemail.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class ConsumerEmailListener {

    private final SendEmailService service;

    @KafkaListener(groupId = "grupo-email", topics = "envio-mensagem", containerFactory = "concurrentContainerFactory")
    public void listener(@Payload EnvioMensagemResponseDTO envioMensagemResponse) {
       log.info("Recebi o email: {}", envioMensagemResponse.toString());
       service.sendEmail(envioMensagemResponse);
    }

}
