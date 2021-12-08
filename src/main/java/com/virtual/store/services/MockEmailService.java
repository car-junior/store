package com.virtual.store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

/** Simulação de envio de email **/
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
}
