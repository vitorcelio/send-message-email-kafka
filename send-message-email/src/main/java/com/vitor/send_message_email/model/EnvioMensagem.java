package com.vitor.send_message_email.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ENVIO_MENSAGEM")
public class EnvioMensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENVIO_MENSAGEM", nullable = false)
    private Integer id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "NUMERO", nullable = false)
    private String numero;

    @Column(name = "ENVIO_WHATSAPP", nullable = false)
    private boolean envioWhatsapp;

    @Column(name = "ENVIO_EMAIL", nullable = false)
    private boolean envioEmail;

    @Column(name = "TITULO", nullable = false)
    private String titulo;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "TEXTO", nullable = false)
    private String texto;

    @PrePersist
    public void prePersist(){
        this.envioWhatsapp = false;
        this.envioEmail = false;
    }

}
