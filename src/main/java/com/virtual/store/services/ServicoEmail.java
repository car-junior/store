package com.virtual.store.services;

import com.virtual.store.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface ServicoEmail {

    void enviarConfirmacaoPedidoEmail(Pedido pedido);

    void enviarEmail(SimpleMailMessage mensagemEmail);
}
