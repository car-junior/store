package com.virtual.store.services;

import com.virtual.store.domain.Cliente;
import com.virtual.store.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public interface ServicoEmail {

    /** envio de email plano sem formatacao e html **/
    void enviarConfirmacaoPedidoEmail(Pedido pedido);
    void enviarEmail(SimpleMailMessage mensagemEmail);

    /** envio de email com formatacao em html **/
    void enviarConfirmacaoPedidoEmailHtml(Pedido pedido);
    void enviarEmailHtml(MimeMessage mensagemEmail);

    /** enviar nova senha para cliente **/
    void enviarNovaSenhaClienteEmail(Cliente cliente, String novaSenha);
}
