package com.vitor.send_message_email.repositories;

import com.vitor.send_message_email.model.EnvioMensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioMensagemRepository extends JpaRepository<EnvioMensagem, Integer> {
}
