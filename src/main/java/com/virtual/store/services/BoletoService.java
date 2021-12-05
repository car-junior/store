package com.virtual.store.services;

import com.virtual.store.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto pagamentoComBoleto, Date instantePedido){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instantePedido);
        /** SETANDO 7 DIAS NA DATA PASSADA **/
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        /** RETORNANDO DATA COM 7 DIAS DE VENCIMENTO E ATRIBUINDO AO VENCIMENTO DO BOLETO **/
        pagamentoComBoleto.setDataVencimento(calendar.getTime());
    }
}
