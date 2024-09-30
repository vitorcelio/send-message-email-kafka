package com.vitor.send_message_email.controller;

import com.vitor.send_message_email.dto.request.EnvioMensagemRequestDTO;
import com.vitor.send_message_email.dto.response.EnvioMensagemResponseDTO;
import com.vitor.send_message_email.services.EnvioMensagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/envioMensagem")
public class EnvioMensagemController {

    private final EnvioMensagemService service;

    @PostMapping
    public ResponseEntity<EnvioMensagemResponseDTO> enviarMensagem(@RequestBody @Valid EnvioMensagemRequestDTO envioMensagem) {
        return new ResponseEntity<>(service.save(envioMensagem), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioMensagemResponseDTO> getMensagem(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.getMensagemEnviada(id), HttpStatus.OK);
    }

}
