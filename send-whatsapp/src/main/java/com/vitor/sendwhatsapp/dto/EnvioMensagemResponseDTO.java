package com.vitor.sendwhatsapp.dto;

import lombok.*;

@ToString
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
