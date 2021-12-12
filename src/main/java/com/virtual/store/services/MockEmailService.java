package com.virtual.store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/** Simulação de envio de email **/
@Service
public class MockEmailService extends AbstractEmailService{
    /** mostrar email no logger do servidor **/
    /** logger referente a classe ServicoEmail **/
    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void enviarEmail(SimpleMailMessage mensagemEmail) {
        LOG.info("Simulando envio de email...");
        LOG.info(mensagemEmail.toString());
        LOG.info("Email enviado!");
    }

    @Override
    public void enviarEmailHtml(MimeMessage mensageEmail) {
        LOG.info("Simulando envio de email com HTML ...");
        LOG.info(mensageEmail.toString());
        LOG.info("Email enviado!");
    }


}
