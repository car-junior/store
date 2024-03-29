package com.virtual.store.config;

import com.virtual.store.services.DBService;
import com.virtual.store.services.MockEmailService;
import com.virtual.store.services.ServicoEmail;
import com.virtual.store.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {
    @Autowired
    private DBService dbService;

    /** CRIANDO ATRIBUTO PARA VALIDAR A CRIACAO DO BANCO SOMENTE QUANDO O DDL FOR CREATE **/
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlGeracaoBanco;

    @Bean
    public boolean instanciandoBandoDeDados() throws ParseException {
        if (!"create".equals(ddlGeracaoBanco)){
            return false;
        }

        dbService.instanciandoTesteBancoDeDados();
        return true;
    }

    /** o método @Bean torna o método disponivel como um componente no projeto **/
    @Bean
    public ServicoEmail servicoEmail(){
        return new SmtpEmailService();
    }

}
