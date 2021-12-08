package com.virtual.store.services;

import com.virtual.store.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface ServicoEmail {

    /** envio de email plano sem formatacao e html **/
    void enviarConfirmacaoPedidoEmail(Pedido pedido);
    void enviarEmail(SimpleMailMessage mensagemEmail);

    /** envio de email com formatacao em html **/
    void enviarConfirmacaoPedidoEmailHtml(Pedido pedido);
    void enviarEmailHtml(MimeMessage mensagemEmail);
}
