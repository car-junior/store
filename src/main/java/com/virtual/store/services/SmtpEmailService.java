package com.virtual.store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {
    @Autowired
    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    /** cria um objeto mailSender a partir das configs no arquivo properties com tadas as informacoes para envio de email **/
    @Autowired
    private MailSender mailSender;

    @Override
    public void enviarEmail(SimpleMailMessage mensagemEmail) {
        LOG.info("Enviando email aguarde...");
        /** usando mailSender para envio de email **/
        mailSender.send(mensagemEmail);
        LOG.info("Email enviado!");
    }
}
