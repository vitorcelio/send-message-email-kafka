package com.vitor.send_message_email.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvioMensagemRequestDTO {

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email é inválido")
    private String email;

    @NotBlank(message = "O número é obrigatório")
    @Length(min = 9, max = 11, message = "O número deve ter entre 9 e 11 caracteres.")
    private String numero;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "O texto é obrigatório")
    private String texto;

}
