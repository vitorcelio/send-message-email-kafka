package com.vitor.sendemail.service;

import com.vitor.sendemail.dto.EnvioMensagemResponseDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Service
public class SendEmailService {

    private static final String TEMPLATE_NAME = "envioEmail";
    private static final String LOGO_EMAIL = "templates/images/logo.png";
    private static final String PNG_MIME_TYPE = "image/png";

    private final Environment env;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final KafkaTemplate<String, EnvioMensagemResponseDTO> kafkaTemplate;

    public void sendEmail(EnvioMensagemResponseDTO envioMensagem) {

        try {
            String mailFrom = env.getProperty("spring.mail.username");
            String mailName = env.getProperty("mail.from.name");

            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper email;
            email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.setFrom(new InternetAddress(mailFrom, mailName));
            email.setTo(envioMensagem.getEmail());
            email.setSubject(envioMensagem.getTitulo());

            Context context = new Context(LocaleContextHolder.getLocale());
            context.setVariable("name", String.format("Olá, %s", envioMensagem.getEmail()));
            context.setVariable("logo", LOGO_EMAIL);
            context.setVariable("descricao", envioMensagem.getDescricao());
            context.setVariable("texto", envioMensagem.getTexto());

            final String html = templateEngine.process(TEMPLATE_NAME, context);

            email.setText(html, true);

            ClassPathResource classPathResource = new ClassPathResource(LOGO_EMAIL);

            email.addInline("logo", classPathResource, PNG_MIME_TYPE);

            mailSender.send(mimeMessage);

            envioMensagem.setEnvioEmail(true);

        } catch (MessagingException | UnsupportedEncodingException e) {
            envioMensagem.setEnvioEmail(false);
            e.printStackTrace();
        }

        sendResponseEnvioEmail(envioMensagem);
    }

    private void sendResponseEnvioEmail(EnvioMensagemResponseDTO envioMensagem) {
        kafkaTemplate.send("response-email", envioMensagem);
    }

}
