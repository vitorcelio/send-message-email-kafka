package com.vitor.send_message_email.services;

import com.vitor.send_message_email.dto.request.EnvioMensagemRequestDTO;
import com.vitor.send_message_email.dto.request.EnvioMensagemResponseDTO;
import com.vitor.send_message_email.exceptions.NotFoundEnvioMensagemException;
import com.vitor.send_message_email.model.EnvioMensagem;
import com.vitor.send_message_email.repositories.EnvioMensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EnvioMensagemService {

    private final EnvioMensagemRepository repository;
    private final KafkaTemplate<String, EnvioMensagemResponseDTO> kafkaTemplate;
    private static final String TOPIC_MENSAGEM = "envio-mensagem";

    public EnvioMensagemResponseDTO save(EnvioMensagemRequestDTO envioMensagemRequestDTO) {

        try {
            EnvioMensagem envioMensagem = new EnvioMensagem();
            BeanUtils.copyProperties(envioMensagemRequestDTO, envioMensagem);
            repository.save(envioMensagem);

            EnvioMensagemResponseDTO dto = EnvioMensagemResponseDTO.builder()
                    .id(envioMensagem.getId())
                    .email(envioMensagem.getEmail())
                    .numero(envioMensagem.getNumero())
                    .titulo(envioMensagem.getTitulo())
                    .descricao(envioMensagem.getDescricao())
                    .texto(envioMensagem.getTexto())
                    .envioWhatsapp(envioMensagem.isEnvioWhatsapp())
                    .envioEmail(envioMensagem.isEnvioEmail())
                    .build();

            sendMessage(dto);

            return dto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EnvioMensagemResponseDTO getMensagemEnviada(Integer id) {
        EnvioMensagem envioMensagem = repository.findById(id)
                .orElseThrow(() -> new NotFoundEnvioMensagemException("Envio da mensagem não encontrado."));

        return EnvioMensagemResponseDTO.builder()
                .id(envioMensagem.getId())
                .email(envioMensagem.getEmail())
                .numero(envioMensagem.getNumero())
                .titulo(envioMensagem.getTitulo())
                .descricao(envioMensagem.getDescricao())
                .texto(envioMensagem.getTexto())
                .envioWhatsapp(envioMensagem.isEnvioWhatsapp())
                .envioEmail(envioMensagem.isEnvioEmail())
                .build();
    }

    private void sendMessage(EnvioMensagemResponseDTO envioMensagem) {
        kafkaTemplate.send(TOPIC_MENSAGEM, envioMensagem);
    }

}
