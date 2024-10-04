package com.vitor.send_message_email.services;

import com.vitor.send_message_email.dto.request.EnvioMensagemRequestDTO;
import com.vitor.send_message_email.dto.response.EnvioMensagemResponseDTO;
import com.vitor.send_message_email.exceptions.NotFoundEnvioMensagemException;
import com.vitor.send_message_email.model.EnvioMensagem;
import com.vitor.send_message_email.repositories.EnvioMensagemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
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

    public void update(EnvioMensagemResponseDTO envioMensagem, boolean isEmail) {

        try {
            EnvioMensagem mensagem =
                    repository.findById(envioMensagem.getId()).orElseThrow(() -> new NotFoundEnvioMensagemException(
                            "Envio da mensagem não encontrado"));

            if(isEmail){
                mensagem.setEnvioEmail(envioMensagem.isEnvioEmail());
            } else {
                mensagem.setEnvioWhatsapp(envioMensagem.isEnvioWhatsapp());
            }

            repository.save(mensagem);
            log.info("Envio da mensagem atualizada: {}", mensagem);
        } catch (Exception e) {
            log.error("Erro ao atualizar mensagem", e);
            e.printStackTrace();
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
