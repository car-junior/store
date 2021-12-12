package com.virtual.store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEmailService extends AbstractEmailService {
    @Autowired
    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    /** cria um objeto mailSender a partir das configs no arquivo properties com tadas as informacoes para envio de email **/
    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void enviarEmail(SimpleMailMessage mensagemEmail) {
        LOG.info("Enviando email aguarde...");
        /** usando mailSender para envio de email **/
        mailSender.send(mensagemEmail);
        LOG.info("Email enviado!");
    }

    @Override
    public void enviarEmailHtml(MimeMessage mensageEmail) {
        LOG.info("Enviando email Html aguarde...");
        /** usando mailSender para envio de email **/
        javaMailSender.send(mensageEmail);
        LOG.info("Email enviado!");
    }
}
