package com.vitor.sendwhatsapp.service;

import com.vitor.sendwhatsapp.clients.ZapiWhatsappClient;
import com.vitor.sendwhatsapp.dto.EnvioMensagemResponseDTO;
import com.vitor.sendwhatsapp.dto.request.SendMessageTextRequest;
import com.vitor.sendwhatsapp.dto.response.SendMessageTextResponse;
import com.vitor.sendwhatsapp.utils.WhatsappUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class SendWhatsappService {

    @Value("${app.whatsapp.instance}")
    private String instance;

    @Value("${app.whatsapp.token}")
    private String token;

    private final ZapiWhatsappClient zapiWhatsappClient;
    private final KafkaTemplate<String, EnvioMensagemResponseDTO> kafkaTemplate;

    public void enviarMensagemWhatsapp(EnvioMensagemResponseDTO envioMensagem) {

        try {

            String message = String.format("%s \n%s \n\n%s", WhatsappUtil.bold(envioMensagem.getTitulo()), WhatsappUtil.code(envioMensagem.getDescricao()), envioMensagem.getTexto());

            var request = SendMessageTextRequest.builder()
                            .phone(envioMensagem.getNumero())
                            .message(message)
                            .build();

            SendMessageTextResponse response = zapiWhatsappClient.sendText(instance, token, request);
            log.info("Mensagem enviada com sucesso");

            var result = response != null ? true : false;

            envioMensagem.setEnvioWhatsapp(result);

            sendResponseEnvioMensagem(envioMensagem);
        } catch (Exception e) {
            log.error("Erro ao enviar Mensagem", e);
            e.printStackTrace();
        }

    }

    private void sendResponseEnvioMensagem(EnvioMensagemResponseDTO envioMensagem) {
        kafkaTemplate.send("response-whatsapp", envioMensagem);
    }

}
