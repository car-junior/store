package com.virtual.store.services;

import com.virtual.store.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public abstract class AbstractEmailService implements ServicoEmail{

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Value("${default.sender}")
    private String remetente;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

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

    @Override
    public void enviarConfirmacaoPedidoEmailHtml(Pedido pedido){
        try {
            MimeMessage mimeMessage = prepararMimeMessageParaPedido(pedido);
            enviarEmailHtml(mimeMessage);
        }
        catch (MessagingException e) {
            enviarConfirmacaoPedidoEmail(pedido);
        }

    }

    public MimeMessage prepararMimeMessageParaPedido(Pedido pedido) throws MessagingException {
        /** pegando pedido gerando objeto do tipo MimeMessage **/
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        /** formar de atribuir valores a essa mimeMessage com o MimeMessageHelper**/
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(pedido.getCliente().getEmail());
        mimeMessageHelper.setFrom(remetente);
        mimeMessageHelper.setSubject("Pedido Confirmado! Código: " + pedido.getId());
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        /** HTML PROCESSADO A PARTIR DO MÉTODO stringTemplateHtmlEmail -- true indica que é um html **/
        mimeMessageHelper.setText(stringTemplateHtmlEmail(pedido), true);
        return mimeMessage;
    };

    protected String stringTemplateHtmlEmail(Pedido pedido){
        Context context = new Context();
        /** enviando objeto pedido para o template **/
        context.setVariable("pedido", pedido);
        /** processando template para retornar html em forma de string **/
        return templateEngine.process("confirmacaoPedido", context);
    }
}
