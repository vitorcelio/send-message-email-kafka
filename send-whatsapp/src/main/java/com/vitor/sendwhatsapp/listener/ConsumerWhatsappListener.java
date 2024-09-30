package com.vitor.sendwhatsapp.listener;

import com.vitor.sendwhatsapp.dto.EnvioMensagemResponseDTO;
import com.vitor.sendwhatsapp.service.SendWhatsappService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class ConsumerWhatsappListener {

    private final SendWhatsappService sendWhatsappService;

    @KafkaListener(groupId = "grupo-mensagem", topics = "envio-mensagem", containerFactory = "concurrentConsumerFactory")
    public void listener(@Payload EnvioMensagemResponseDTO envioMensagemResponse) {
        log.info("Mensagem recebida: {}", envioMensagemResponse.toString());
        sendWhatsappService.enviarMensagemWhatsapp(envioMensagemResponse);
    }

}
