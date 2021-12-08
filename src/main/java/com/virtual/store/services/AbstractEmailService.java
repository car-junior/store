package com.virtual.store.services;

import com.virtual.store.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractEmailService implements ServicoEmail{

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Value("${default.sender}")
    private String remetente;

    @Override
    public void enviarConfirmacaoPedidoEmail(Pedido pedido){
        try {
            SimpleMailMessage simpleMailMessage = prepararMensagemParaPedido(pedido);
            enviarEmail(simpleMailMessage);
        }
        catch (ParseException e) {
            e.getStackTrace();
        }
    }

    protected SimpleMailMessage prepararMensagemParaPedido(Pedido pedido) throws ParseException {
        SimpleMailMessage sm = new SimpleMailMessage();

        /** destinatario **/
        sm.setTo(pedido.getCliente().getEmail());

        /** remetente **/
        sm.setFrom(remetente);

        /** assunto email **/
        sm.setSubject("Pedido Confirmado! Código: " + pedido.getId());

        /** garantindo que será criado uma data a partir do servidor que está sendo executado **/
        String dataPedido = sdf.format(new Date(System.currentTimeMillis()));
        sm.setSentDate(sdf.parse(dataPedido));

        /** corpo do email **/
        sm.setText(pedido.toString());

        return sm;
    }
}
