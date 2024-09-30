package com.vitor.send_message_email.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvioMensagemResponseDTO {

    private Integer id;
    private String email;
    private String numero;
    private String titulo;
    private String descricao;
    private String texto;
    private boolean envioWhatsapp;
    private boolean envioEmail;

}
